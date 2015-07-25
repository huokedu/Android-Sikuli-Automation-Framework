package aspirin.framework.core.pageobjects.hotspotshield;

import aspirin.framework.core.utilities.Global;
import org.sikuli.script.Pattern;

/**
 * Created by Artur on 7/9/2015.
 */
public final class AccountObjects {

    public static final Pattern Account_SignInButton(Float precision){
        return new Pattern(AccountImages.Account_SignInButton).similar(precision);
    }

    public static final Pattern Account_SignOutButton(Float precision){
        return new Pattern(AccountImages.Account_SignOutButton).similar(precision);
    }

    public static final Pattern Account_FullTopBar_MyAccount(Float precision){
        return new Pattern(AccountImages.Account_FullTopBar_MyAccount).similar(precision);
    }

    public static final Pattern Account_FullTopBar_SignIn(Float precision){
        return new Pattern(AccountImages.Account_FullTopBar_SignIn).similar(precision);
    }

    public static final Pattern Account_TopActivityBar_MyAccount(Float precision){
        return new Pattern(AccountImages.Account_TopActivityBar_MyAccount).similar(precision);
    }

    public static final Pattern Account_TopActivityBar_SingIn(Float precision){
        return new Pattern(AccountImages.Account_TopActivityBar_SingIn).similar(precision);
    }

    public static final Pattern Account_CreateAccount(Float precision){
        return new Pattern(AccountImages.Account_CreateAccount).similar(precision);
    }

    public static final Pattern Account_ForgotPassword(Float precision){
        return new Pattern(AccountImages.Account_ForgotPassword).similar(precision);
    }

    public static final Pattern Account_UsernameIcon(Float precision){
        return new Pattern(AccountImages.Account_UsernameIcon).similar(precision);
    }

    public static final Pattern Account_PasswordIcon(Float precision){
        return new Pattern(AccountImages.Account_PasswordIcon).similar(precision);
    }

    public static final Pattern Account_Username(Float precision){
        return new Pattern(AccountImages.Account_Username).similar(precision);
    }

    public static final Pattern Account_Password(Float precision){
        return new Pattern(AccountImages.Account_Password).similar(precision);
    }

    public static final Pattern Account_AccountStatus_Elite(Float precision){
        return new Pattern(AccountImages.Account_AccountStatus_Elite).similar(precision);
    }

    public static final Pattern Account_Dialog_Incorrect(Float precision){
        return new Pattern(AccountImages.Account_Dialog_Incorrect).similar(precision);
    }

    public static final Pattern Account_Dialog_EnterPassword(Float precision){
        return new Pattern(AccountImages.Account_Dialog_EnterPassword).similar(precision);
    }

    public static final Pattern Account_Dialog_EnterUsername(Float precision){
        return new Pattern(AccountImages.Account_Dialog_EnterUsername).similar(precision);
    }

    public static final Pattern Account_Dialog_DeviceLimit(Float precision){
        return new Pattern(AccountImages.Account_Dialog_DeviceLimit).similar(precision);
    }

    public static final Pattern Account_Dialog_EmailNotFound(Float precision){
        return new Pattern(AccountImages.Account_Dialog_EmailNotFound).similar(precision);
    }

    public static final Pattern Account_ForgotPassword_SendPasswordButton(Float precision){
        return new Pattern(AccountImages.Account_ForgotPassword_SendPasswordButton).similar(precision);
    }

    public static final Pattern Account_Dialog_EmailNotValid(Float precision){
        return new Pattern(AccountImages.Account_Dialog_EmailNotValid).similar(precision);
    }

    public static final Pattern Account_ForgotPassword_TopActivityBar(Float precision){
        return new Pattern(AccountImages.Account_ForgotPassword_TopActivityBar).similar(precision);
    }

}

class AccountImages{

    private static final String absoluteAIMPath = Global.Img_FolderPath();

    public static final String HotspotShield_Activities_MyAccount = absoluteAIMPath + "Hotspot Shield\\Activities\\My Account\\";
    public static final String Account_FullTopBar_MyAccount = HotspotShield_Activities_MyAccount + "myAccountFullTopBar.png";
    public static final String Account_FullTopBar_SignIn = HotspotShield_Activities_MyAccount + "signInfullTopBar.png";
    public static final String Account_TopActivityBar_MyAccount = HotspotShield_Activities_MyAccount + "myAccountTopActivityBar.png";
    public static final String Account_TopActivityBar_SingIn = HotspotShield_Activities_MyAccount + "signInTopActivityBar.png";
    public static final String Account_SignInButton = HotspotShield_Activities_MyAccount + "signInButton.png";
    public static final String Account_SignOutButton = HotspotShield_Activities_MyAccount + "signOutButton.png";
    public static final String Account_CreateAccount = HotspotShield_Activities_MyAccount + "createNewAccountLink.png";
    public static final String Account_ForgotPassword = HotspotShield_Activities_MyAccount + "forgotPasswordLink.png";
    public static final String Account_UsernameIcon = HotspotShield_Activities_MyAccount + "usernameIcon.png";
    public static final String Account_PasswordIcon = HotspotShield_Activities_MyAccount + "passwordIcon.png";
    public static final String Account_Username = HotspotShield_Activities_MyAccount + "username.png";
    public static final String Account_Password = HotspotShield_Activities_MyAccount + "password.png";
    public static final String Account_AccountStatus_Elite = HotspotShield_Activities_MyAccount + "eliteAccountType.png";
    public static final String Account_Dialog_Incorrect = HotspotShield_Activities_MyAccount + "incorrectDialog.png";
    public static final String Account_Dialog_EnterPassword = HotspotShield_Activities_MyAccount + "enterPasswordDialog.png";
    public static final String Account_Dialog_EnterUsername = HotspotShield_Activities_MyAccount + "enterUsernameDialog.png";
    public static final String Account_Dialog_EmailNotFound = HotspotShield_Activities_MyAccount + "emailNotFound.png";
    public static final String Account_ForgotPassword_SendPasswordButton = HotspotShield_Activities_MyAccount + "sendPasswordButton.png";
    public static final String Account_Dialog_EmailNotValid = HotspotShield_Activities_MyAccount + "enterValidEmailAddress.png";
    public static final String Account_ForgotPassword_TopActivityBar = HotspotShield_Activities_MyAccount + "forgotPasswordTopActivityBar.png";
    public static final String Account_Dialog_DeviceLimit = HotspotShield_Activities_MyAccount + "5devicesError.png";
}
