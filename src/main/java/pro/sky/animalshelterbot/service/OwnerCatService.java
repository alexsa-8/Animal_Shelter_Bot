package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerCat;
import pro.sky.animalshelterbot.exception.NumberNotFoundException;
import pro.sky.animalshelterbot.exception.OwnerCatNotFoundException;
import pro.sky.animalshelterbot.exception.ProbationNotSpecifiedException;
import pro.sky.animalshelterbot.repository.OwnerCatRepository;

import java.io.File;
import java.util.Collection;

/**
 * Сервис OwnerCatService
 * Сервис используется для создания, редактирования, удаления и получения владельца кота/списка владельцев котов из БД
 * @author Gubina Marina
 */

@Service
public class OwnerCatService {
    private final TelegramBot bot;
    private final OwnerCatRepository repository;

    private final static Logger log = LoggerFactory.getLogger(OwnerCat.class);

    public OwnerCatService(TelegramBot bot, OwnerCatRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    /**
     * Создание владельца кота и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#save(Object)}
     * @param owner создается объект владелец кота
     * @param status устанавливается статус владельца
     * @return созданный владелец
     */
    public OwnerCat create(OwnerCat owner, OwnerStatus status) {
        log.info("Request to create owner cat {}", owner);
        owner.setStatus(status);
        return repository.save(owner);
    }

    /**
     * Поиск владельца кота в БД по id
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#findById(Object)}
     * @param id идентификатор владельца
     * @throws OwnerCatNotFoundException, если владелец с указанным id не найден
     * @return найденный владелец
     */
    public OwnerCat find(Long id) {
        log.info("Request to getting owner cat  by id {}", id);
        return repository.findById(id).orElseThrow(OwnerCatNotFoundException::new);
    }

    /**
     * Поиск владельца кота в БД по chat id
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#findById(Object)}
     * @param chatId идентификатор владельца
     * @throws OwnerCatNotFoundException, если владелец с указанным chat id не найден
     * @return найденный владелец
     */
    public OwnerCat findByChatId(Long chatId){
        log.info("Request to getting owner dog  by chat chat id {}", chatId);
        return repository.findByChatId(chatId);
    }

    /**
     * Изменение данных владельца кота в БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#save(Object)}
     * @param owner изменяемый владелец
     * @throws OwnerCatNotFoundException, если указанный владелец кота не найден
     * @return измененный владелец кота
     */
    public OwnerCat update(OwnerCat owner) {
        log.info("Request to update owner cat  {}", owner);
        if (owner.getId() != null) {
            if (find(owner.getId()) != null) {
                return repository.save(owner);
            }
        }
        log.error("Request owner cat is not found");
        throw new OwnerCatNotFoundException();
    }

    /**
     * Получение списка владельцев котов из БД
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#findAll()}
     * @return список владельцев котов
     */
    public Collection<OwnerCat> getAll() {
        log.info("Request to getting all owner cat");
        return repository.findAll();
    }

    /**
     * Удаление владельца кота из БД по id
     * <br>
     * Используется метод репозитория {@link OwnerCatRepository#delete(Object)}
     * @param id идентификатор владельца кота
     */
    public void delete(Long id) {
        log.info("Request to delete owner cat by id {}", id);
        repository.deleteById(id);
    }

    /**
     * Изменение количества дней испытательного срока
     * @param id идентификатор владельца
     * @param number количество дней, на которое изменяется (1 - 14, 2 - 30)
     * @return владелец с измененным испытательным сроком
     */
    public OwnerCat changeNumberOfReportDays(Long id, Long number) {
        OwnerCat ownerCat = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner cat with id = {}", id);
            return new OwnerCatNotFoundException();
        });
        if (ownerCat.getNumberOfReportDays() == null) {
            log.error("Owner has no probation, id = {}", id);
            throw new ProbationNotSpecifiedException();
        }
        if (number == 1) {
            log.info("The trial period has been extended by 14 days, id = {}", id);
            ownerCat.setNumberOfReportDays(ownerCat.getNumberOfReportDays() + 14);
            bot.execute(new SendMessage(ownerCat.getChatId(), "Вам продлили период испытательного срока на 14 дней"));

        } else if (number == 2) {
            log.info("The trial period has been extended by 30 days, id = {}", id);
            ownerCat.setNumberOfReportDays(ownerCat.getNumberOfReportDays() + 30);
            bot.execute(new SendMessage(ownerCat.getChatId(), "Вам продлили период испытательного срока на 30 дней"));
        }
        return repository.save(ownerCat);
    }
    /**
     * Изменение статуса владельца
     * @param id идентификатор владельца
     * @param status выбираемый статус
     * @return владелец с измененным статусом
     */
    public OwnerCat updateStatus(Long id, OwnerStatus status) {
        log.info("Request to update owner cat status {}", status);
        OwnerCat ownerDog = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner cat with id = {}", id);
            return new OwnerCatNotFoundException();
        });
        ownerDog.setStatus(status);
        bot.execute(new SendMessage(ownerDog.getChatId(), "Вам присвоен статус " + ownerDog.getStatus().getDescription()));
        return repository.save(ownerDog);
    }

    /**
     * Уведомление владельцу от волонтера
     * @param id идентификатор владельца
     * @param number номер команды
     * @return измененные данные владельца + уведомление
     */
    public OwnerCat noticeToOwners(Long id, Long number) {
        OwnerCat ownerCat = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner cat with id = {}", id);
            return new OwnerCatNotFoundException();
        });
        if (number == 1) {
            log.info("Notification owner cat about bad report, id = {}", id);
            bot.execute(new SendMessage(ownerCat.getChatId(), "«Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                    "Пожалуйста, подойди ответственнее к этому занятию. " +
                    "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного».")
            );
        } else if (number == 2) {
            log.info("Successful completion of the probationary period, id = {}", id);
            ownerCat.setStatus(OwnerStatus.APPROVED);
            bot.execute(new SendMessage(ownerCat.getChatId(), "Вы прошли испытательный срок."));
        } else if (number == 3) {
            log.info("The probationary period has not passed, id = {}", id);
            ownerCat.setStatus(OwnerStatus.IN_BLACK_LIST);
            bot.execute(new SendMessage(ownerCat.getChatId(), "Вы не прошли испытательный срок."));
            String pathDog = "src/main/resources/list_documents/Manual.pdf";
            File file = new File(pathDog);
            SendDocument document = new SendDocument(ownerCat.getChatId(), file);
            document.caption("Ознакомьтесь с инструкцией");
            bot.execute(document);
        } else {
            throw new NumberNotFoundException();
        }
        return repository.save(ownerCat);
    }
}
