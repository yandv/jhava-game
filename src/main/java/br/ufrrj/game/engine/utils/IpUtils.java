package br.ufrrj.game.engine.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class IpUtils {

    public static String getIpAddress(SocketChannel socketChannel) {
        try {
            InetSocketAddress address = (InetSocketAddress) socketChannel.getRemoteAddress();
            String currentAddress = address.getAddress().getHostAddress() + ":" + address.getPort();
            return currentAddress;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
