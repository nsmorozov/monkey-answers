package org.example;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Text;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MonkeyAnswer {

    private static final String URL = "https://ru.surveymonkey.com/r/G2Z237H";
    private static final int DEV_INDEX = 19;
    private static final String BOXES = "input[id*=alternative]";
    private static final String CHECKBOXES = ".checkbox-button-container input";
    private static final String SUBMIT_BTN = "button[type='submit']";
    private static final String FINAL_MESSAGE = "h1[class=hero-text-1]";

    public static void main( String[] args )
    {
        Selenide.open(URL);

        //fill department
        $(".select").selectOption(DEV_INDEX);

        //fill all sliders(big boxes) randomly but with the overall positive attitude
        $$(BOXES).forEach(e -> {
            int randomInt = new Random().ints(1, 2, 4).findFirst().getAsInt();
            e.setValue(String.valueOf(randomInt));
        });

        //fill one small mood checkbox randomly
        Random r = new Random();
        List<SelenideElement> checkboxes = $$(CHECKBOXES);
        checkboxes.get(r.nextInt(checkboxes.size())).click();

        //Submit page
        $(SUBMIT_BTN).click();

        //Verify success message on the next page
        $(FINAL_MESSAGE).shouldHave(Text.exactTextCaseSensitive("Хотите создавать опросы сами?"));
    }
}
