package pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;

public class Node {
    public int id;
    public double longitude;
    public double latitude;
    public int visitDuration;
    public int timeFromDepot;
    public int timeToDepot;
    public String address;

    public Node() {}

    public boolean isDepto() {
        return this.id == 0;
    }

    public String toString() {
        return String.format("%f %f %d %d %d %s", longitude, latitude, visitDuration, timeFromDepot, timeToDepot, address);
    }

    private static class InstanceReader {
        private InputStreamReader in;
        private static final int BUFFER_SIZE = 8192;
        private char[] buf;
        private int pos; // index of next unread char = number of already used chars in buffer
        private int full; // number of chars in buffer

        public InstanceReader(String filename) throws FileNotFoundException, IOException {
            try {
                in = new InputStreamReader(new FileInputStream(new File(filename)), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                in = new InputStreamReader(new FileInputStream(new File(filename)));
            }
            buf = new char[BUFFER_SIZE];
            pos = 0;
            full = in.read(buf, 0, BUFFER_SIZE);
        }

        protected int ensureAvailable(int num) throws IOException {
            int avail = full - pos;
            if(avail >= num || full != BUFFER_SIZE) {
                return avail;
            }
            System.arraycopy(buf, pos, buf, 0, avail);
            pos = 0;
            full = avail;
            int read = in.read(buf, avail, BUFFER_SIZE - avail);
            if(read > 0) {
                full += read;
            }
            return full;
        }

        public int readInt(char nextChar) throws IOException {
            ensureAvailable(10);
            int num = 0;
            char c;
            while((c = buf[pos++]) != nextChar) {
                num = num * 10 + (c - '0');
            }
            return num;
        }

        public double readDouble(char nextChar) throws IOException {
            ensureAvailable(34);
            char c;
            int num = 0;
            while((c = buf[pos++]) != '.') {
                num = num * 10 + (c - '0');
            }
            int denom = 1;
            while((c = buf[pos++]) != nextChar) {
                num = num * 10 + (c - '0');
                denom *= 10;
            }
            return (double)num / (double)denom;
        }

        public String readString(char nextChar) throws IOException {
            StringBuilder sb = new StringBuilder();
            while(true) {
                int start = pos;
                while(pos < full) {
                    if(buf[pos] == nextChar) {
                        sb.append(buf, start, pos - start);
                        pos++; // consume nextChar
                        return sb.toString();
                    }
                    pos++;
                }
                sb.append(buf, start, pos - start);
                if(full != BUFFER_SIZE) {
                    return sb.toString();
                }
                pos = 0;
                int read = in.read(buf, 0, BUFFER_SIZE);
                if(read > 0) {
                    full = read;
                } else {
                    full = 0;
                }
            }
        }

        public void close() throws IOException {
            in.close();
        }
    }

    public static List<Node> readInstance(String filename) throws FileNotFoundException, IOException {
        Node.InstanceReader ir = new Node.InstanceReader(filename);
        int n = ir.readInt('\n');
        List<Node> nodes = new ArrayList<Node>(n);
        for(int i = 0;i < n;i++) {
            Node node = new Node();
            node.id = i;
            node.longitude = ir.readDouble(' ');
            node.latitude = ir.readDouble(' ');
            node.visitDuration = ir.readInt(' ');
            node.timeFromDepot = ir.readInt(' ');
            node.timeToDepot = ir.readInt(' ');
            node.address = ir.readString('\n');
            nodes.add(node);
        }
        ir.close();
        return nodes;
    }
}
