package puregero.multipaper.server.handlers;

import puregero.multipaper.server.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

public class ReadAdvancementsHandler implements Handler {
    @Override
    public void handle(ServerConnection connection, DataInputStream in, DataOutputSender out) throws IOException {
        String world = in.readUTF();
        String uuid = in.readUTF();

        readAndSendAdvancementsData(world, uuid, out);
    }

    private void readAndSendAdvancementsData(String world, String uuid, DataOutputSender out) {
        try {
            byte[] b = FileLocker.readBytes(new File(new File(world, "advancements"), uuid + ".json"));
            out.writeUTF("advancementsData");
            out.writeInt(b.length);
            out.write(b);
            out.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
