@Service
@RequiredArgsConstructor
public class BankManagementServiceImpl implements BankManagementService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // ...

    @Override
    @Transactional // Гарантує, що сесія буде відкрита протягом всього методу
    public List<UserDto> getUsersByBank(Long bankId) {
        final List<User> users = userRepository.findAllByBankId(bankId);
        // Тут ми можемо безпечно звертатися до лінивих полів, якби вони нам були потрібні
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

// ...
