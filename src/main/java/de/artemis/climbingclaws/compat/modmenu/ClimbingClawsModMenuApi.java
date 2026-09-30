package de.artemis.climbingclaws.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.artemis.climbingclaws.client.ClimbingClawsConfigScreen;

public final class ClimbingClawsModMenuApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ClimbingClawsConfigScreen::new;
    }
}
