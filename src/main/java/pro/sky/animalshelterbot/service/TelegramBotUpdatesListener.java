package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.Commands;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.repository.DogRepository;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Сервис TelegramBotUpdatesListener
 * Сервис для обработки доступных обновлений в чате
 * @author Kilikova Anna
 * @author Bogomolov Ilya
 * @see UpdatesListener
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    /**
     * Поле: объект, который запускает события журнала.
     */
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Поле: телеграм бот
     */
    private final TelegramBot telegramBot;
    private final DogRepository dogRepository;

    private final static Pattern PATTERN_MESSAGE = Pattern.compile(
            "([\\W+]+)(\\s)(\\+7\\d{3}[-.]?\\d{3}[-.]?\\d{4})(\\s)([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)");


    private final OwnerDogRepository ownerDogRepository;
    /**
     * Конструктор
     *
     * @param telegramBot   телеграм бот
     * @param dogRepository бд собак
     * @param ownerDogRepository бл владельцев
     */
    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      DogRepository dogRepository, OwnerDogRepository ownerDogRepository) {
        this.telegramBot = telegramBot;
        this.dogRepository = dogRepository;
        this.ownerDogRepository = ownerDogRepository;
        telegramBot.execute(new SetMyCommands(
                new BotCommand(Commands.START.getTitle(), Commands.START.getDescription()),
                new BotCommand(Commands.INFO.getTitle(), Commands.INFO.getDescription()),
                new BotCommand(Commands.CALL_VOLUNTEER.getTitle(), Commands.CALL_VOLUNTEER.getDescription())
        ));
    }

    /**
     * Метод, выполняющийся после инициализации объекта, задает обработчик обновлений
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработчик обратного вызова с доступными обновлениями
     * @param updates доступные обновления
     * @return id последнего обработанного обновления, которое не нужно доставлять повторно
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                if (update.message() != null) {
                    String message = update.message().text();
                    if (message.equals(Commands.START.getTitle())) {
                        greeting(update);
                        description(update);
                    } else if (message.equals(Commands.INFO.getTitle())) {
                        info(update);
                    } else if (message.equals(Commands.CALL_VOLUNTEER.getTitle())) {
                        volunteerMenu(update);
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Команда не найдена повторите запрос"));
                    }
                } else if (update.callbackQuery() != null) {
                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                            update.callbackQuery().data()));
                    //startMenu
                    if (update.callbackQuery() != null && update.callbackQuery().data().equals(" ")) {
                        telegramBot.execute(startMenu(update));
                    }
                    else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.BACK.getCallbackData())){
                        telegramBot.execute(startMenu(update));
                    }
                    else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.BACK_TO_ANIMAL_MENU.getCallbackData())){
                        telegramBot.execute(animalInfoMenu(update));
                    }
                    //shelterInfoMenu
                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.INFO.getCallbackData())) {
                        telegramBot.execute(shelterInfoMenu(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.SHELTER_RECOMMENDATIONS.getCallbackData())) {
                        telegramBot.execute(shelterRecommendation(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.SHELTER_DATA.getCallbackData())) {
                        telegramBot.execute(shelterData(update));}
                    //animalInfoMenu
                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.ANIMAL_INFO.getCallbackData())) {
                        telegramBot.execute(animalInfoMenu(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.DATING_RULES.getCallbackData())) {
                        telegramBot.execute(datingRules(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.LIST_DOCUMENTS.getCallbackData())) {
                        listDocuments(update);
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.RECOMMENDATIONS.getCallbackData())) {
                        telegramBot.execute(recommendationMenu(update));
                    }
                    //подменю по рекомендациям
                    else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.RECOMMENDATIONS_TRANSPORTATION.getCallbackData())){
                        telegramBot.execute(recommendationsTransportation(update));
                    }else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.RECOMMENDATIONS_DOG.getCallbackData())){
                        telegramBot.execute(recommendationsDog(update));
                    }else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.RECOMMENDATIONS_PUPPY.getCallbackData())){
                        telegramBot.execute(recommendationsPuppy(update));
                    }else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.RECOMMENDATIONS_DISABLED_DOG.getCallbackData())){
                        telegramBot.execute(recommendationsDisabledDog(update));
                    }

                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.ADVICES.getCallbackData())) {
                        telegramBot.execute(advicesMenu(update));
                    }
                    // подменю по советам
                    else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.ADVICES_CYNOLOGISTS.getCallbackData())){
                        telegramBot.execute(advicesCynologists(update));
                    }else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.LIST_CYNOLOGISTS.getCallbackData())){
                        telegramBot.execute(listCynologists(update));
                    }else if(update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.REASONS_REFUSAL.getCallbackData())){
                        telegramBot.execute(reasonsRefusal(update));
                    }

                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.CONTACT_DETAILS.getCallbackData())) {
                        telegramBot.execute(contactDetails(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.CALL_VOLUNTEER.getCallbackData())) {
                        telegramBot.execute(volunteerMenu(update));
                    }

                    //submitReportMenu
                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.SUBMIT_REPORT.getCallbackData())) {
                        telegramBot.execute(submitReportMenu(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.REPORT_FORM.getCallbackData())) {
                        telegramBot.execute(reportForm(update));
                    } else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.CALL_VOLUNTEER.getCallbackData())) {
                        telegramBot.execute(volunteerMenu(update));
                    }

                    //volunteerMenu
                    else if (update.callbackQuery() != null && update.callbackQuery().data()
                            .equals(Commands.CALL_VOLUNTEER.getCallbackData())) {
                        telegramBot.execute(volunteerMenu(update));
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод для сохранения контактных данных пользователя
     *
     * @param update доступное обновление
     */
    private SendMessage contactDetails(Update update) {
        String text = update.message().text();
        try {
            if (text != null) {
                Matcher matcher = PATTERN_MESSAGE.matcher(text);
                if (matcher.find()) {
                    String name = matcher.group(1);
                    String phone = matcher.group(3);
                    int age = Integer.parseInt(matcher.group(5));
                    OwnerDog ownerDog = new OwnerDog();
                    ownerDog.setChatId(update.message().chat().id());
                    ownerDog.setName(name);
                    ownerDog.setPhone(phone);
                    ownerDog.setAge(age);
                    ownerDog.setStatus(OwnerStatus.IN_SEARCH);
                    ownerDogRepository.save(ownerDog);
                    SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                            "Контакты пользователя сохранились, скоро с Вами свяжутня");
                    telegramBot.execute(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Что-то ввели неверно");
        }
        return null;
    }


    /**
     * Метод, выдающий советы пользователю
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage advices(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(), "Советы по уходу и всему всему, отдельное меню надо");
        return message;
    }

    /**
     * Метод, выдающий рекомендации о технике безопасности пользователю
     * @param update доступное обновление
     * @return документ с рекомендации по технике безопасности
     */
    private SendDocument shelterRecommendation(Update update) {
        String path = "src/main/resources/shelterInfo/Safety_in_shelter.pdf";
        File recommendation = new File(path);
        SendDocument sendDocument = new SendDocument(update.callbackQuery().message().chat().id(),
                recommendation);
        sendDocument.caption("Рекомендации по технике безопасности на территории " +
                "приюта в прикрепленном документе \u2191 ");
        return sendDocument;
    }

    /**
     * Метод, выдающий список документов для позователя
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private void listDocuments(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Лист документов");
        telegramBot.execute(message);
    }

    /**
     * Метод, выдающий информацию по знакомству с питомцем для пользователя
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage datingRules(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Тут все про знакомство с питомцем");
        return message;
    }

    /**
     * Метод, присылающий форму отчета для пользователя
     * @param update доступное обновление
     * @return форма отчета
     */
    private SendMessage reportForm(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Форма отчета");
        return message;
    }

    /**
     * Метод, присылающий информацию по связи с волонтером для пользователя
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage volunteerMenu(Update update) {
        SendMessage volunteer = new SendMessage(update.callbackQuery().message().chat().id(), "Волонтер скоро с вами свяжется\uD83D\uDE09");
        return volunteer;
    }

    /**
     * Метод, вызывающий подменю по отчетам
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    private SendMessage submitReportMenu(Update update) {
        SendMessage report = new SendMessage(update.callbackQuery().message().chat().id(), "Затычка");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.REPORT_FORM.getDescription())
                        .callbackData(Commands.REPORT_FORM.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );

        report.replyMarkup(inlineKeyboardMarkup);

        return report;
    }

    /**
     * Метод, вызывающий подменю по животным
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    private SendMessage animalInfoMenu(Update update) {
        SendMessage animalInfo = new SendMessage(update.callbackQuery().message().chat().id(),
                "Вы можете ознакомиться со всеми особенностями, с которыми нам с вами предстоид столкнуться, " +
                        "прежде чем вы сможете найти себе нового друга");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.DATING_RULES.getDescription())
                        .callbackData(Commands.DATING_RULES.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.LIST_DOCUMENTS.getDescription())
                        .callbackData(Commands.LIST_DOCUMENTS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ADVICES.getDescription())
                        .callbackData(Commands.ADVICES.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CONTACT_DETAILS.getDescription())
                        .callbackData(Commands.CONTACT_DETAILS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );

        animalInfo.replyMarkup(inlineKeyboardMarkup);

        return animalInfo;
    }

    /**
     * Метод, присылающий информацию по приюту для пользователя
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage shelterInfoMenu(Update update) {
        SendMessage shelterInfo = new SendMessage(update.callbackQuery().message().chat().id(),
                "Здесь вы можете получить всю информацию по приюту.");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SHELTER_DATA.getDescription())
                        .callbackData(Commands.SHELTER_DATA.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SHELTER_RECOMMENDATIONS.getDescription())
                        .callbackData(Commands.SHELTER_RECOMMENDATIONS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK.getDescription())
                        .callbackData(Commands.BACK.getCallbackData())
        );
        shelterInfo.replyMarkup(inlineKeyboardMarkup);
        return shelterInfo;
    }

    /**
     * Метод, присылающий приветствие для пользователя
     * @param update доступное обновление
     */
    public void greeting(Update update) {
        logger.info("Greeting to " + update.message().text());
        SendMessage greeting = new SendMessage(update.message().chat().id(),
                "Привет, " + update.message().from().firstName() + "! \uD83D\uDE42 \n" +
                        "\nЯ создан для того, что-бы помочь тебе найти друга, четвероного друга." +
                        "Я живу в приюте для животных и рядом со мной находятся брошенные питомцы, " +
                        "потерявшиеся при переезде, пережившие своих хозяев или рожденные на улице. " +
                        "Поначалу животные в приютах ждут\uD83D\uDC15, что за ними вернутся старые владельцы. \uD83D\uDC64" +
                        "Потом они ждут своих друзей-волонтеров \uD83D\uDC71\uD83C\uDFFB\u200D♂️ \uD83D\uDC71\uD83C\uDFFB\u200D♀️, " +
                        "корм по расписанию⌛, посетителей, которые погладят и почешут за ухом.❤️ " +
                        "Но больше всего приютские подопечные ждут, что их заберут домой.\uD83C\uDFE0 \n");

        telegramBot.execute(greeting);
    }

    /**
     * Метод для запуска меню
     * @param update доступное обновление
     * @return меню для пользователя с кнопками
     */
    public SendMessage startMenu(Update update) {
        String message = "Отлично, тут ты можешь узнать всю необходимую информацию о приюте и животных, " +
                "если понадобится помощь, ты всегда можешь позвать волонтера";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.INFO.getDescription())
                        .callbackData(Commands.INFO.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ANIMAL_INFO.getDescription())
                        .callbackData(Commands.ANIMAL_INFO.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.SUBMIT_REPORT.getDescription())
                        .callbackData(Commands.SUBMIT_REPORT.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.CALL_VOLUNTEER.getDescription())
                        .callbackData(Commands.CALL_VOLUNTEER.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("Расскажи о нас")
                        .switchInlineQuery("Помоги найти новый дом питомцам!")
        );


        SendMessage mes = new SendMessage(update.callbackQuery().message().chat().id(), message);
        mes.replyMarkup(inlineKeyboardMarkup);

        return mes;
    }

    public void description(Update update) {
        logger.info("Description to " + update.message().text());
        String desc = update.message().from().firstName() + ", может ты и есть тот самый хозяин, который подарит новый дом нашему другу?";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton("ДА").callbackData(" "),
                new InlineKeyboardButton("Я еще подумаю").callbackData("Мы будем тебя ждать!")
        );

        SendMessage description = new SendMessage(update.message().chat().id(), desc);
        description.replyMarkup(inlineKeyboardMarkup);

        telegramBot.execute(description);

    }

    /**
     * Метод, выдающий информацию для пользователя
     * @param update доступное обновление
     */
    // method sends information to the user
    public void info(Update update) {
        logger.info("Info to " + update.message().text());
        String infoMsg = "«Приют» — слово, от которого становится тоскливо.\uD83D\uDE14" +
                "«Приют для животных» звучит еще более безрадостно.\uD83D\uDE22" +
                "Это место, где живут брошенные питомцы, потерявшиеся при переезде, пережившие своих хозяев или рожденные на улице. \uD83C\uDF27" +
                "\n" +
                "Поначалу животные в приютах ждут\uD83D\uDC15, что за ними вернутся старые владельцы. \uD83D\uDC64" +
                "Потом они ждут своих друзей-волонтеров \uD83D\uDC71\uD83C\uDFFB\u200D♂️ \uD83D\uDC71\uD83C\uDFFB\u200D♀️, " +
                "корм по расписанию⌛, посетителей, которые погладят и почешут за ухом.❤️ " +
                "Но больше всего приютские подопечные ждут, что их заберут домой.\uD83C\uDFE0 " +
                "Если вы решили взять питомца из приюта, то мы с радостью расскажем, как это сделать!\uD83D\uDC4D";

        SendMessage info = new SendMessage(update.message().chat().id(), infoMsg);
        telegramBot.execute(info);
    }

    // Подменю по рекомендациям

    private SendMessage recommendationMenu(Update update){
        String message = "Список рекомендаций";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_TRANSPORTATION.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_TRANSPORTATION.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_PUPPY.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_PUPPY.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_DOG.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_DOG.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.RECOMMENDATIONS_DISABLED_DOG.getDescription())
                        .callbackData(Commands.RECOMMENDATIONS_DISABLED_DOG.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK_TO_ANIMAL_MENU.getDescription())
                        .callbackData(Commands.BACK_TO_ANIMAL_MENU.getCallbackData())
        );

        SendMessage recom = new SendMessage(update.callbackQuery().message().chat().id(), message);
        recom.replyMarkup(inlineKeyboardMarkup);
        return recom;
    }
    // Подменю по советам
    private SendMessage advicesMenu(Update update){
        String message = "Список советов";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.ADVICES_CYNOLOGISTS.getDescription())
                        .callbackData(Commands.ADVICES_CYNOLOGISTS.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.LIST_CYNOLOGISTS.getDescription())
                        .callbackData(Commands.LIST_CYNOLOGISTS.getCallbackData())

        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.REASONS_REFUSAL.getDescription())
                        .callbackData(Commands.REASONS_REFUSAL.getCallbackData())
        );
        inlineKeyboardMarkup.addRow(
                new InlineKeyboardButton(Commands.BACK_TO_ANIMAL_MENU.getDescription())
                        .callbackData(Commands.BACK_TO_ANIMAL_MENU.getCallbackData())
        );

        SendMessage advices = new SendMessage(update.callbackQuery().message().chat().id(), message);
        advices.replyMarkup(inlineKeyboardMarkup);
        return advices;
    }
    // Метод обработки запроса на предоставления данных о приюте

    private SendMessage shelterData(Update update){
        logger.info("Shelter data ");
        String dataMessage = "Инфо по приюту: расписание, адрес, схема, контактные данные";
        SendMessage data = new SendMessage(update.callbackQuery().message().chat().id(), dataMessage);
        return data;
    }

    private SendMessage  recommendationsTransportation(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Рекомендации по транспортировке");
        return message;
    }

    private SendMessage  recommendationsDog(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Рекомендации по уходу за собакой");
        return message;
    }

    private SendMessage  recommendationsPuppy(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Рекомендации по уходу за щенком");
        return message;
    }

    private SendMessage recommendationsDisabledDog(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Рекомендации по обустройству собаки с ограниченными возможностями");
        return message;
    }

    /**
     * Метод, выдающий советы пользователю
     * @param update доступное обновление
     * @return сообщение пользователю
     */
    private SendMessage advicesCynologists(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Советы от кинолога");
        return message;
    }
    private SendMessage  listCynologists(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Лист проверенных кинологов");
        return message;
    }

    private SendMessage reasonsRefusal(Update update) {
        SendMessage message = new SendMessage(update.callbackQuery().message().chat().id(),
                "Причины отказа");
        return message;
    }

    // The method calls the volunteer


}
