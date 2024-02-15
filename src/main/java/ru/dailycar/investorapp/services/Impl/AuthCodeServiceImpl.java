package ru.dailycar.investorapp.services.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dailycar.investorapp.dto.SendNotificationRequest;
import ru.dailycar.investorapp.entities.CodeType;
import ru.dailycar.investorapp.entities.User;
import ru.dailycar.investorapp.exceptions.NotFoundException;
import ru.dailycar.investorapp.repositories.UserRepository;
import ru.dailycar.investorapp.services.AuthCodeService;
import ru.dailycar.investorapp.services.RedisService;
import ru.dailycar.investorapp.sources.NotificationSource;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthCodeServiceImpl implements AuthCodeService {

    private final RedisService redisService;

    //возможно финал не нужен
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final NotificationSource notificationSource;

    @Override
    public String createCode(String username, CodeType type) {
        Optional<User> optionalExistedUser = userRepository.findByEmailOrPhoneNumber(username);
        Integer code = generateCode();
        String key = username + "_" + type.toString();
        redisService.set(key, code.toString());
        if (optionalExistedUser.isPresent()) {
            User existedUser = optionalExistedUser.get();
            return notificationSource.sendNotification(SendNotificationRequest.createCodeNotification(username, existedUser.getId(), code));
        } else {
            if (!(type.equals(CodeType.SIGNUP))) {
                throw new NotFoundException("Пользователь не найден!");
            } else {
                return notificationSource.sendNotification(SendNotificationRequest.createCodeNotification("Гость", "empty", code));
            }
        }
    }

    @Override
    public Boolean checkCode(String username, String code , CodeType type) throws BadRequestException {
//       todo: Dev dont forgot to comment
        if (code.equals("1234")) {
            return true;
        }

        String createdCode = redisService.get(username + "_" + type.toString()).toString();
        if (code.equals(createdCode)) {
            return true;
        } else throw new BadRequestException("Неверный код!");
    }

    @Override
    public Integer generateCode() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    @Override
    public Boolean deleteCode(String username, CodeType type) {
        return redisService.delete(username + "_" + type.toString());
    }


}
