package Irukai.example.test.client;

import Irukai.example.test.client.screen.BackpackScreen;
import Irukai.example.test.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class TestClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("클라이언트: 화면 등록 중..."); // 로그 추가
        // 서버의 ScreenHandler 데이터와 클라이언트의 Screen 화면을 연결합니다.
        HandledScreens.register(ModScreens.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
    }
}