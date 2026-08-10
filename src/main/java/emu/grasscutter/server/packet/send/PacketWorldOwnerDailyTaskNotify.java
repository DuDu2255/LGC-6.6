package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldOwnerDailyTaskNotifyOuterClass.WorldOwnerDailyTaskNotify;

public class PacketWorldOwnerDailyTaskNotify extends BasePacket {
    public PacketWorldOwnerDailyTaskNotify(Player player) {
        super(PacketOpcodes.WorldOwnerDailyTaskNotify);

        var manager = player.getDailyTaskManager();

        var notify =
                WorldOwnerDailyTaskNotify.newBuilder()
                        .setFilterCityId(manager.getCityId())
                        .setFinishedDailyTaskNum(manager.getFinishedCount());

        manager.getDailyTasks()
                .forEach(task -> notify.addTaskList(task.toProto()));

        this.setData(notify.build());
    }
}