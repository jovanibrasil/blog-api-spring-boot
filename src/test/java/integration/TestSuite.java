package integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import integration.auth.AuthIT;
import integration.images.ImageIT;
import integration.posts.CreatePostIT;
import integration.subscriptions.SubscriptionIT;
import integration.users.CreateUserIT;

@RunWith(Suite.class)
@SuiteClasses({
	ImageIT.class,
	CreatePostIT.class,
	SubscriptionIT.class,
	CreateUserIT.class,
	AuthIT.class
})
public class TestSuite {

}
