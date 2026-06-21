import java.io.IOException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
public class Main 
{
    public static void main(String[] args) 
    {
        try
        {
            // Get hostname
            String hostname = InetAddress.getLocalHost().getHostName();
            System.out.println("Hostname: "+hostname);

            // Get memory
            List<String> lines = Files.readAllLines(Paths.get("/proc/meminfo"));
            for (String Line : lines)
            {
                if(Line.startsWith("MemTotal"))
                {
                    System.out.println(Line);
                }
                if(Line.startsWith("MemAvailable"))
                {
                    System.out.println(Line);
                }
            }

            // Get uptime
            String uptime = Files.readString(Paths.get("/proc/uptime"));
            System.out.println(uptime);

            // Disk usage
            File root = new File("/");
            long total = root.getTotalSpace();
            long free = root.getFreeSpace();
            System.out.println("Total : "+total+" free : "+free);

            // Network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements())
            {
                NetworkInterface ni = interfaces.nextElement();
                System.out.println(ni.getName());
            }

            // CPU usage

        }
        catch(UnknownHostException e)
        {
            System.out.println("Error "+e);
        }
        catch(IOException e)
        {
            System.out.println("Error "+e);
        }
    }
    
}
