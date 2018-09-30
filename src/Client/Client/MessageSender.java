package Client;

import java.io.BufferedWriter;
import java.io.DataOutputStream;

import org.json.simple.JSONObject;

public class MessageSender {

	private BufferedWriter outputStream;

	public MessageSender(BufferedWriter outputStream) {

		this.outputStream = outputStream;
	}

	public void sendMessage(JSONObject json) {

		try {
			System.out.println("send "+json.toJSONString());
			outputStream.write(json.toString()+"\n");
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
