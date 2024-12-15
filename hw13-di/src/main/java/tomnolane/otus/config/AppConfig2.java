package tomnolane.otus.config;

import tomnolane.otus.appcontainer.api.AppComponent;
import tomnolane.otus.appcontainer.api.AppComponentsContainerConfig;
import tomnolane.otus.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig2 {

    @AppComponent(order = 0, name = "SomeGame1")
    public SomeGame1 someGame1() {
        return new SomeGame1Impl();
    }

    @AppComponent(order = 0, name = "SomeGame2")
    public SomeGame2 someGame2() {
        return new SomeGame2Impl();
    }

    @AppComponent(order = 1, name = "someService")
    public GameService someService(
            SomeGame1 someGame1, SomeGame2 someGame2) {
        return new GameServiceImpl(someGame1, someGame2);
    }
}
