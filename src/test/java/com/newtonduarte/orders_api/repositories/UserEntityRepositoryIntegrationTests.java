package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserEntityRepositoryIntegrationTests {
    private final UserRepository underTest;

    @Autowired
    public UserEntityRepositoryIntegrationTests(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled() {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        underTest.save(testUserEntityA);

        Optional<UserEntity> result = underTest.findById(testUserEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUserEntityA);
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled() {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        underTest.save(testUserEntityA);

        UserEntity testUserEntityB = TestDataUtils.createTestUserEntityB();
        underTest.save(testUserEntityB);

        UserEntity testUserEntityC = TestDataUtils.createTestUserEntityC();
        underTest.save(testUserEntityC);

        Iterable<UserEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).containsExactly(testUserEntityA, testUserEntityB, testUserEntityC);
    }

    @Test
    public void testThatUserCanBeUpdated() {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        underTest.save(testUserEntityA);

        testUserEntityA.setName("UPDATED");
        underTest.save(testUserEntityA);

        Optional<UserEntity> result = underTest.findById(testUserEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUserEntityA);
        assertThat(result.get().getName()).isEqualTo("UPDATED");
    }

    @Test
    public void testThatUserCanBeDeleted() {
        UserEntity testUserEntityA = TestDataUtils.createTestUserEntityA();
        underTest.save(testUserEntityA);
        underTest.deleteById(testUserEntityA.getId());

        Optional<UserEntity> result = underTest.findById(testUserEntityA.getId());

        assertThat(result).isEmpty();
    }
}
