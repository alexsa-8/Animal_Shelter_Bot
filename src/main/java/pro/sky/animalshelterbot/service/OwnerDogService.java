package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.exception.NumberNotFoundException;
import pro.sky.animalshelterbot.exception.OwnerDogNotFoundException;
import pro.sky.animalshelterbot.repository.OwnerDogRepository;

import java.util.Collection;

/**
 * Сервис OwnerDogService
 * Сервис используется для создания, редактирования, удаления и получения владельца собаки/списка владельцев собак из БД
 * @author Kilikova Anna
 */

@Service
public class OwnerDogService {
    private final OwnerDogRepository repository;
    private final static Logger log = LoggerFactory.getLogger(OwnerDog.class);

    public OwnerDogService(OwnerDogRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание владельца собаки и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#save(Object)}
     * @param ownerDog создается объект владелец собаки
     * @param status устанавливается статус владельца
     * @return созданный владелец
     */
    public OwnerDog create(OwnerDog ownerDog, OwnerStatus status) {
        log.info("Request to create owner dog {}", ownerDog);
        ownerDog.setStatus(status);
        return repository.save(ownerDog);
    }

    /**
     * Поиск владельца собаки в БД по id
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#findById(Object)}
     * @param id идентификатор владельца
     * @throws OwnerDogNotFoundException, если владелец с указанным id не найден
     * @return найденный владелец
     */
    public OwnerDog find(Long id) {
        log.info("Request to getting owner dog  by id {}", id);
        return repository.findById(id).orElseThrow(OwnerDogNotFoundException::new);
    }

    /**
     * Поиск владельца собаки в БД по chatId
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#findById(Object)}
     * @param chatId идентификатор владельца
     * @throws OwnerDogNotFoundException, если владелец с указанным chatId не найден
     * @return найденный владелец
     */
    public OwnerDog findByChatId(Long chatId){
        log.info("Request to getting owner dog  by chat id {}", chatId);
        return repository.findByChatId(chatId);
    }

    /**
     * Изменение данных владельца собаки в БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#save(Object)}
     * @param ownerDog изменяемый владелец
     * @throws OwnerDogNotFoundException, если указанный владелец собаки не найден
     * @return измененный владелец собаки
     */
    public OwnerDog update(OwnerDog ownerDog) {
        log.info("Request to update owner dog  {}", ownerDog);
        if (ownerDog.getId() != null) {
            if (find(ownerDog.getId()) != null) {
                return repository.save(ownerDog);
            }
        }
        log.error("Request owner dog is not found");
        throw new OwnerDogNotFoundException();
    }

    /**
     * Получение списка владельцев собак из БД
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#findAll()}
     * @return список владельцев собак
     */
    public Collection<OwnerDog> getAll() {
        log.info("Request to getting all owner dog");
        return repository.findAll();
    }

    /**
     * Удаление владельца собаки из БД по id
     * <br>
     * Используется метод репозитория {@link OwnerDogRepository#delete(Object)}
     * @param id идентификатор владельца собаки
     */
    public void delete(Long id) {
        log.info("Request to delete owner dog by id {}", id);
        repository.deleteById(id);
    }

    /**
     * Изменение количества дней испытательного срока
     * @param id идентификатор владельца
     * @param number количество дней, на которое изменяется (1 - 14, 2 - 30)
     * @return владелец с измененным испытательным сроком
     */
    public OwnerDog changeNumberOfReportDays(Long id, Long number) {
        OwnerDog ownerDog = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner dog with id = {}", id);
            return new OwnerDogNotFoundException();
        });
        if (ownerDog.getNumberOfReportDays() == null) {
            throw new RuntimeException();
        }
        if (number == 1) {
            ownerDog.setNumberOfReportDays(ownerDog.getNumberOfReportDays() + 14);
            SendMessage message = new SendMessage(ownerDog.getChatId(), "Вам продлили период испытательного срока на 14 дней");

        } else if (number == 2) {
            ownerDog.setNumberOfReportDays(ownerDog.getNumberOfReportDays() + 30);
            SendMessage message = new SendMessage(ownerDog.getChatId(), "Вам продлили период испытательного срока на 30 дней");
        } else {
            throw new NumberNotFoundException();
        }
        return repository.save(ownerDog);
    }

    /**
     * Изменение статуса владельца
     * @param id идентификатор владельца
     * @param status выбираемый статус
     * @return владелец с измененным статусом
     */
    public OwnerDog updateStatus(Long id, OwnerStatus status) {
        log.info("Request to update owner dog status {}", status);
        OwnerDog ownerDog = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner dog with id = {}", id);
            return new OwnerDogNotFoundException();
        });
        ownerDog.setStatus(status);
        return repository.save(ownerDog);
    }

    public OwnerDog noticeToOwners(Long id, Long number) {
        OwnerDog ownerDog = repository.findById(id).orElseThrow(() -> {
            log.error("There is not owner dog with id = {}", id);
            return new OwnerDogNotFoundException();
        });
        if (number == 1) {
            SendMessage message = new SendMessage(ownerDog.getChatId(), "«Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                    "Пожалуйста, подойди ответственнее к этому занятию. " +
                    "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного».");
        } else if (number == 2) {
            ownerDog.setStatus(OwnerStatus.APPROVED);
            SendMessage message = new SendMessage(ownerDog.getChatId(), "Вы прошли испытательный срок.");
        } else if (number == 3) {
            ownerDog.setStatus(OwnerStatus.IN_BLACK_LIST);
            SendMessage message = new SendMessage(ownerDog.getChatId(), "Вы не прошли испытательный срок.");
        } else {
            throw new NumberNotFoundException();
        }
        return repository.save(ownerDog);
    }
}
