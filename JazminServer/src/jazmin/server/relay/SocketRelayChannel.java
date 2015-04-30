/**
 * 
 */
package jazmin.server.relay;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * @author yama
 *
 */
public class SocketRelayChannel extends RelayChannel{
	Channel serverChannel;
	//
	SocketRelayChannel(TransportType type,String localAddress, int localPort) {
		super(type, localAddress, localPort);
	}
	//
	@Override
	void close() throws Exception {
		super.close();
		if(serverChannel!=null){
			serverChannel.close();
		}
	}
	//
	@Override
	void sendData(ByteBuf buffer) {
		super.sendData(buffer);
		if(outboundChannel!=null&&outboundChannel.isActive()){
			ByteBuf buf= Unpooled.copiedBuffer(buffer);
			packetSentCount++;
			byteSentCount+=buffer.capacity();
			outboundChannel.writeAndFlush(buf);
		}
	}
}
