package aspirin.framework.core.elite;

import aspirin.framework.Runner;
import aspirin.framework.core.actionobjects.hotspotshield.GeneralSettingsActions;
import aspirin.framework.core.actionobjects.hotspotshield.MainActions;
import aspirin.framework.core.actionobjects.hotspotshield.MenuActions;
import aspirin.framework.core.utilities.Global;
import aspirin.framework.core.utilities.SpecialUtils;
import org.sikuli.script.FindFailed;

/**
 * Created by support on 7/20/15.
 */
public class PresetElite {

    public static void setDebugOptions(String domainID, Boolean enableHydra) throws FindFailed {

        MenuActions.pauseProtection();
        GeneralSettingsActions.setVPNServer("", true);
        GeneralSettingsActions.setDebugDomain(domainID, true);
        GeneralSettingsActions.setVPNMode(enableHydra, true);
        GeneralSettingsActions.resolveDomains(false, true);
        GeneralSettingsActions.disablePopUps(false);
        SpecialUtils.forceStopApplication(SpecialUtils.HOTSPOTSHIELD_PACKAGE_NAME);
        SpecialUtils.openActivity(SpecialUtils.HOTSPOTSHIELD_MAIN_ACTIVITY);
        MainActions.startVPN(false);
        Global.setVar_VPNMode(false);
        Global.setVar_DisablePopUps(false);
        Global.setVar_setDebugDomain(false);
    }
}
