package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginApiTest {

    private static final String BASE       = "https://swaper.com";
    private static final String LOGIN_PATH = "/rest/public/login";

    @BeforeAll
    public void setUp() {
        RestAssured.baseURI = BASE;
    }

    @Test
    @DisplayName("Login returns complete user payload")
    public void loginAndAssertAllFields() {
        Response res = given()
            .contentType("application/json")
            .body(Map.of(
                "name",              "testuser@qa.com",
                "password",          "Parole123",
                "recaptchaToken",    ""
            ))
            .when().post(LOGIN_PATH)
            .then().extract().response();

        // Print full JSON for review
        System.out.println("=== LOGIN RESPONSE ===");
        System.out.println(res.prettyPrint());
        System.out.println("======================");

        assertEquals(200, res.statusCode(), "Login endpoint must return 200");

        // Extract everything
        String username      = res.path("username");
        Object permissions   = res.path("permissions");
        String investorType  = res.path("investorType");
        String regStep       = res.path("registrationStep");
        String status        = res.path("status");
        String firstName     = res.path("firstName");
        Float  accountBal    = res.path("accountBalance");
        Float  unsettledBal  = res.path("unsettledBalance");
        List<?> banks        = res.path("bankAccountNumbers");
        String number        = res.path("number");
        String idDocStatus   = res.path("idDocumentsStatus");
        Boolean displayPort  = res.path("displayPortfolio");
        Boolean inUpload     = res.path("inUploadStep");
        Boolean vip          = res.path("vip");
        Boolean vipConfirmed = res.path("vipConfirmed");
        Integer unreadPush   = res.path("notifications.unreadPushNotifications");
        String currency      = res.path("currency");
        String language      = res.path("language");
        Boolean changedCurr  = res.path("hasChangedCurrency");
        String registeredAt  = res.path("registeredAt");
        Boolean showPep      = res.path("isShowPep");
        Boolean pepOrOther   = res.path("isPepOrPrivateInvestorNotOwner");
        Boolean showAgree    = res.path("isShowLastAgreementPopup");
        Boolean showKyc      = res.path("isShowKycPopup");
        Boolean show2fa      = res.path("isShowTwoFaPopup");
        Boolean using2fa     = res.path("usingTwoFa");
        Boolean updPersonal  = res.path("updatePersonalData");
        Boolean updBirth     = res.path("shouldUpdatePrivateInvestorBirthDate");
        Boolean showPlAssign = res.path("isShowPlAssignmentAgreementPopup");
        Boolean canDelete    = res.path("isDeleteRequestAllowed");
        Boolean showProdType = res.path("isShowProductTypePopup");
        Boolean showRates    = res.path("isShowInterestRatesPopup");
        Object estTaxData    = res.path("updateEstoniaTaxData");
        Integer id           = res.path("id");

        assertAll("full payload validation",
            () -> assertEquals("testuser@qa.com", username,    "username"),
            () -> assertNull(permissions,                      "permissions"),
            () -> assertEquals("PRIVATE_PERSON", investorType,"investorType"),
            () -> assertEquals("IDENTIFICATION", regStep,      "registrationStep"),
            () -> assertEquals("REGISTERED", status,          "status"),
            () -> assertEquals("Tester", firstName,           "firstName"),
            () -> assertEquals(0.00f, accountBal,             "accountBalance"),
            () -> assertEquals(0.00f, unsettledBal,           "unsettledBalance"),
            () -> assertTrue(banks.isEmpty(),                 "bankAccountNumbers"),
            () -> assertEquals("52940", number,               "number"),
            () -> assertEquals("NO_DOCUMENTS", idDocStatus,   "idDocumentsStatus"),
            () -> assertFalse(displayPort,                    "displayPortfolio"),
            () -> assertFalse(inUpload,                       "inUploadStep"),
            () -> assertFalse(vip,                            "vip"),
            () -> assertFalse(vipConfirmed,                   "vipConfirmed"),
            () -> assertEquals(0, unreadPush,                 "notifications.unreadPushNotifications"),
            () -> assertEquals("EUR", currency,               "currency"),
            () -> assertEquals("EN", language,                "language"),
            () -> assertTrue(changedCurr,                     "hasChangedCurrency"),
            () -> assertEquals("2024-07-29", registeredAt,     "registeredAt"),
            () -> assertFalse(showPep,                        "isShowPep"),
            () -> assertTrue(pepOrOther,                      "isPepOrPrivateInvestorNotOwner"),
            () -> assertFalse(showAgree,                      "isShowLastAgreementPopup"),
            () -> assertFalse(showKyc,                        "isShowKycPopup"),
            () -> assertFalse(show2fa,                        "isShowTwoFaPopup"),
            () -> assertFalse(using2fa,                       "usingTwoFa"),
            () -> assertFalse(updPersonal,                    "updatePersonalData"),
            () -> assertFalse(updBirth,                       "shouldUpdatePrivateInvestorBirthDate"),
            () -> assertFalse(showPlAssign,                   "isShowPlAssignmentAgreementPopup"),
            () -> assertTrue(canDelete,                       "isDeleteRequestAllowed"),
            () -> assertFalse(showProdType,                   "isShowProductTypePopup"),
            () -> assertFalse(showRates,                      "isShowInterestRatesPopup"),
            () -> assertNull(estTaxData,                      "updateEstoniaTaxData"),
            () -> assertNotNull(id,                            "id")
        );
    }
}