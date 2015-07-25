package unittests;

import aspirin.framework.core.actionobjects.hotspotshield.SmartModeSettingsActions;
import aspirin.framework.core.functional.PresetFUN;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

/**
 * Created by a.spirin on 7/15/2015.
 */
public class SmartModeSettings {

    public static final Integer TO_FULL = 1;
    public static final Integer TO_SELECTED_SITES = 2;
    public static final Integer TO_OFF = 3;
    public static final Integer TO_DEFAULT = 4;
    public static final Integer SAFE_NETWORK = 1;
    public static final Integer UNSAFE_NETWORK = 2;
    public static final Integer MOBILE_NETWORK = 3;
    public static final Integer CURRENT_NETWORK = 4;

    @Before
    public void preset() throws FindFailed {

        PresetFUN.MainActivity(false);
    }

    @Test
    public void MobileFull() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_FULL, false);
    }

    @Test
    public void MobileSS() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, false);
    }

    @Test
    public void MobileOff() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_OFF, false);
    }

    @Test
    public void MobileDefault() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_DEFAULT, false);
    }

    @Test
    public void SafeFull() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_FULL, false);
    }

    @Test
    public void SafeSS() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_SELECTED_SITES, false);
    }

    @Test
    public void SafeOff() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_OFF, false);
    }

    @Test
    public void SafeDefault() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_DEFAULT, false);
    }

    @Test
    public void UnsafeFull() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, false);
    }

    @Test
    public void UnsafeSS() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, false);
    }

    @Test
    public void UnsafeOff() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, false);
    }

    @Test
    public void UnsafeDefault() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, false);
    }

    @Test
    public void CurrentFull() throws FindFailed{

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, true);
    }

    @Test
    public void CurrentSS() throws FindFailed{

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
    }

    @Test
    public void CurrentOff() throws FindFailed{

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
    }

    @Test
    public void CurrentDefault() throws FindFailed{

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, true);
    }

    @Test
    public void LoadTest() throws FindFailed {

        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(CURRENT_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(UNSAFE_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(SAFE_NETWORK, TO_FULL, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_DEFAULT, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_OFF, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_SELECTED_SITES, true);
        SmartModeSettingsActions.setSmartModeFor(MOBILE_NETWORK, TO_FULL, false);
    }
}
