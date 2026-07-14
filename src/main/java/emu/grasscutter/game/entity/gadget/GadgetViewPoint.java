package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.codex.CodexViewpointData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;

public final class GadgetViewPoint extends GadgetContent {
    public GadgetViewPoint(EntityGadget gadget) {
        super(gadget);
    }

    @Override
    public boolean onInteract(Player player, GadgetInteractReq req) {
        int groupId = this.getGadget().getGroupId();
        int configId = this.getGadget().getConfigId();

        Grasscutter.getLogger()
                .warn(
                        "[VIEWPOINT INTERACT DEBUG] entityId={} groupId={} configId={} req={}",
                        this.getGadget().getId(),
                        groupId,
                        configId,
                        req);

        CodexViewpointData viewpoint =
                GameData.getViewCodexByGroupConfig(groupId, configId);

        if (viewpoint == null) {
            Grasscutter.getLogger()
                    .warn(
                            "[VIEWPOINT LOOKUP MISS] groupId={} configId={}",
                            groupId,
                            configId);

            return false;
        }

        Grasscutter.getLogger()
                .warn(
                        "[VIEWPOINT LOOKUP HIT] viewpointId={} groupId={} configId={}",
                        viewpoint.getId(),
                        groupId,
                        configId);

        player.getCodex().checkUnlockedViewPoint(viewpoint);
        return true;
    }

    @Override
    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
    }
}