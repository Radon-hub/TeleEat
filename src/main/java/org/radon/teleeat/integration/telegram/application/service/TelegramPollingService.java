package org.radon.teleeat.integration.telegram.application.service;

import lombok.val;
import org.radon.teleeat.food.application.port.in.GetFoodsPagination;
import org.radon.teleeat.food.domain.Food;
import org.radon.teleeat.integration.telegram.domain.*;
import org.radon.teleeat.order.application.port.in.*;
import org.radon.teleeat.order.domain.Order;
import org.radon.teleeat.order.domain.OrderItem;
import org.radon.teleeat.order.domain.OrderStatus;
import org.radon.teleeat.order.presentation.dto.AddOrderItemRequest;
import org.radon.teleeat.order.presentation.dto.RemoveOrderItemRequest;
import org.radon.teleeat.user.application.port.in.GetOrAddUserUseCase;
import org.radon.teleeat.user.application.port.in.UpdateUserUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramPollingService{

    private final RestTemplate restTemplate = new RestTemplate();
    private final String token = "8533420108:AAFz733ZyNiOP6mtJNc7uB50joh_ToJor3c";
    private Long offset = 0L;


    private final GetFoodsPagination getFoodsPagination;
    private final GetOrAddUserUseCase getOrAddUserUseCase;
    private final AddOrderItemUseCase addOrderItemUseCase;
    private final RemoveOrderItemUseCase removeOrderItemUseCase;
    private final GetUserOpenOrderUseCase getUserOpenOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final UpdateOrderAddressUseCase updateOrderAddressUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public TelegramPollingService(GetFoodsPagination getFoodsPagination, GetOrAddUserUseCase getOrAddUserUseCase, AddOrderItemUseCase addOrderItemUseCase, RemoveOrderItemUseCase removeOrderItemUseCase, GetUserOpenOrderUseCase getUserOpenOrderUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, UpdateOrderAddressUseCase updateOrderAddressUseCase, UpdateUserUseCase updateUserUseCase) {
        this.getFoodsPagination = getFoodsPagination;
        this.getOrAddUserUseCase = getOrAddUserUseCase;
        this.addOrderItemUseCase = addOrderItemUseCase;
        this.removeOrderItemUseCase = removeOrderItemUseCase;
        this.getUserOpenOrderUseCase = getUserOpenOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.updateOrderAddressUseCase = updateOrderAddressUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }


    @Scheduled(fixedDelay = 1000)
    public void poll() {

        String url = "https://api.telegram.org/bot" + token +
                "/getUpdates?timeout=30&offset=" + (offset + 1);

        TelegramResponse response = restTemplate.getForObject(url, TelegramResponse.class);

        if (response == null || !response.isOk()) return;

        for (Update update : response.getResult()) {
            offset = update.getUpdate_id();
            handleUpdate(update);
        }
    }

    private void handleUpdate(Update update) {
        if (update.getMessage() != null) handleMessage(update.getMessage());
        else if (update.getCallback_query() != null) handleCallback(update.getCallback_query());
    }


    private void handleMessage(Message message) {

        Long chatId = message.getChat().getId();
        String text = message.getText();

        if(text.startsWith("/")){
            if("/start".equals(text)) {
                val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
                String greetings;
                if(user.getFullname() != null){
                  greetings = "Hello " + user.getFullname();
                }else{
                  greetings = "Hello";
                }
                sendMessage(chatId, greetings+" and welcome to TeleEat...Do you want to order foods ? ... \n 1. Try enter /menu to see our dishes! \n 2. Try enter /order to see your order and submit");
            }

            if ("/menu".equals(text)) {
                sendFoodList(chatId,getFoodsPagination.getFoods(Pageable.ofSize(50)).stream().toList());
            }

            if("/order".equals(text)) {
                val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
                val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());
                if(openOrder == null){
                    sendMessage(chatId, "You don't have an order yet! ... Try adding some foods with /menu");
                }else{
                    sendUserOrdersList(chatId,openOrder);
                }
            }
            if("/submit".equals(text)) {
                val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
                val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());

                if(openOrder == null || openOrder.getItems() == null || openOrder.getItems().isEmpty()){
                    sendMessage(chatId, "You have to add at least one item to submit your order! \n Try adding some foods with /menu");
                }else{

                    boolean isFullnameMissing = user.getFullname() == null || user.getFullname().isBlank();
                    boolean isPhoneMissing = user.getPhone_number() == null || user.getPhone_number().isBlank();
                    boolean isAddressMissing = openOrder.getAddress() == null || openOrder.getAddress().isBlank();

                    StringBuilder msgBuilder = new StringBuilder();

                    if (isFullnameMissing || isPhoneMissing) {

                        msgBuilder.append("There are some issues in your profile:\n\n");

                        if (isFullnameMissing) {
                            msgBuilder.append("- You didn’t specify your full name\n");
                        }
                        if (isPhoneMissing) {
                            msgBuilder.append("- You didn’t specify your phone number\n");
                        }

                        msgBuilder.append("\nYou can edit these under /profile");

                        sendMessage(chatId, msgBuilder.toString());

                    }else{

                        if(isAddressMissing){
                            sendMessage(chatId, "Please enter your address\n\n * Example = (address:Tehran - Gandi St - 21th Ave ...) ");
                        }else{
                            submitOrderAndDeliver(chatId,openOrder);
                        }


                    }

                }


            }

            if("/profile".equals(text)) {
                val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

                boolean isFullnameMissing = user.getFullname() == null || user.getFullname().isBlank();
                boolean isPhoneMissing = user.getPhone_number() == null || user.getPhone_number().isBlank();

                StringBuilder msgBuilder = new StringBuilder();

                if (isFullnameMissing || isPhoneMissing) {

                    msgBuilder.append("There are some issues in your profile:\n\n");

                    if (isFullnameMissing) {
                        msgBuilder.append("- Full name is missing,Try set it up -> name:Jake william\n");
                    }
                    if (isPhoneMissing) {
                        msgBuilder.append("- Phone number is missing,Try set it up -> phone:09300000000\n");
                    }

                    sendMessage(chatId, msgBuilder.toString());

                }else{
                    sendMessage(chatId, "Your profile is up to date.");
                }

            }
        }

        if(text.startsWith("address:")) {

            val user = getOrAddUserUseCase.getOrAdd(chatId.toString());
            val openOrder = getUserOpenOrderUseCase.getOpenOrder(user.getId());

            val userAddress = text.split(":")[1];

            updateOrderAddressUseCase.updateAddress(openOrder.getId(), userAddress);

            submitOrderAndDeliver(chatId,openOrder);

        }

        if(text.startsWith("name:")) {

            val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

            val userFullName = text.split(":")[1];

            updateUserUseCase.updateUser(user.getId(),userFullName,null);
            sendMessage(chatId, "Your Full name is successfully updated.");
        }

        if(text.startsWith("phone:")) {

            val user = getOrAddUserUseCase.getOrAdd(chatId.toString());

            val userPhoneNumber = text.split(":")[1];

            updateUserUseCase.updateUser(user.getId(),null,userPhoneNumber);
            sendMessage(chatId, "Your phone number is successfully updated.");
        }


    }

    private void submitOrderAndDeliver(Long chatId,Order openOrder){

//          TODO : WE CAN HAVE A PAYMENT HERE
//          updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.PENDING_PAYMENT);

        updateOrderStatusUseCase.updateOrderStatus(openOrder.getId(), OrderStatus.DELIVERY);

        sendMessage(chatId, "Thank you for submitting your order! \n\n Your food is on delivery now. \n\n Enjoy your food with TeleEat ;-) \n\n Bye...");
    }

    private void sendUserOrdersList(Long chatId, Order order) {

        List<List<Map<String, String>>> keyboard = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            keyboard.add(List.of(Map.of(
                    "text", item.getFood().getName() + " * " + item.getCount() + " / " +  item.getFood().getPrice() + "$",
                    "callback_data", "reduce_"+item.getFood().getName()+"_"+item.getId()
            )));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", "Your Order | Total Price : "+ order.getTotalPrice() +"$ \n 1. You can remove items by click on them \n 2. You can submit your order by /submit \n 3. Or you can see menu to add more foods by /menu");
        body.put("reply_markup", Map.of("inline_keyboard", keyboard));

        restTemplate.postForObject("https://api.telegram.org/bot" + token + "/sendMessage", body, String.class);
    }


    private void sendFoodList(Long chatId, List<Food> foodList) {

        List<List<Map<String, String>>> keyboard = new ArrayList<>();
        for (Food food : foodList) {
            keyboard.add(List.of(Map.of(
                    "text", food.getName() + " | " +  food.getPrice().toString()+"$",
                    "callback_data", "add_"+food.getName()+"_"+food.getId().toString()
            )));
        }

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", "Menu : \n 1. To order, just click on your favorite food \n 2. To see your order, Try /order");
        body.put("reply_markup", Map.of("inline_keyboard", keyboard));

        restTemplate.postForObject("https://api.telegram.org/bot" + token + "/sendMessage", body, String.class);
    }

    private void handleCallback(CallbackQuery callback) {
        Long chatId = callback.getFrom().getId();
        String data = callback.getData(); // e.g., "Pizza_1"

        val type = data.split("_")[0];

        val foodName = data.split("_")[1];

        val user = getOrAddUserUseCase.getOrAdd(chatId.toString());


        if(type.equals("add")) {

            val foodID = data.split("_")[2];

            sendMessage(chatId, "You Add One " + foodName);

            addOrderItemUseCase.addOrderItem(new AddOrderItemRequest(user.getId(),Long.valueOf(foodID)));

        }else{

            val orderItemId = data.split("_")[2];

            sendMessage(chatId, "One " + foodName + " Removed from your order");

            removeOrderItemUseCase.removeOrderItem(new RemoveOrderItemRequest(Long.valueOf(orderItemId),user.getId()));

        }


    }

    private void sendMessage(Long chatId, String text) {
        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", text);

        restTemplate.postForObject("https://api.telegram.org/bot" + token + "/sendMessage", body, String.class);
    }

}
