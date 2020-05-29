package sample;

import javax.bluetooth.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceDiscovery {

    public static boolean stopWasRequested = false;
    private static ExecutorService serviceRadio = Executors.newCachedThreadPool();
    private static List<String> mylist = new ArrayList<String>();

    // Constructor of thread
    public DeviceDiscovery() {

        //ExecutorService service = Executors.newCachedThreadPool();
        serviceRadio.submit(new Runnable() {

            @Override
            public void run() {

                Main.DisplayCurrentThread ("Method Start");

                while(!stopWasRequested) {

                    try {

                        Thread.sleep(10);

                        try {

                            DeviceDiscovery.listDevice();
                            Controller.updateBluetoothDeviceLabel();


                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }catch (InterruptedException e){
                        //Do nothing
                    }
                }

            }

        });

    } // end Constructor of thread

    // Method discovery list of devices Bluetooth.

    public static void listDevice() throws IOException, InterruptedException {

        List<String> list = new ArrayList<String>(); // List of radio; name & address.

        final Object inquiryCompletedEvent = new Object();

        list.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {

                String btDeviceName;

                try {

                    btDeviceName = btDevice.getFriendlyName(false);

                    list.add( btDeviceName + "\n" + btDevice.getBluetoothAddress());
                    System.out.println("Device Address : " + btDevice.getBluetoothAddress());
                    System.out.println("Device name    : " + btDevice.getFriendlyName(false) + "\n");


                } catch (IOException cantGetDeviceName) {

                }

            } // end DiscoveryListener.

            public void inquiryCompleted(int discType) {

                System.out.println("Device Inquiry completed!");

                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
                // Implemented method.
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                // Implemented method.
            }

        }; //end listDevice

        synchronized(inquiryCompletedEvent) {

            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);

            if (started) {

                System.out.println("wait for device inquiry to complete...\n");
                inquiryCompletedEvent.wait();


                System.out.println(list.size() +  " device(s) found");
            }

        }

        // At end of discovery, result display on console
        //-----------------------------------------------
        if (  list.size() == 0 ) {
            mylist.add( "No Device(s) Found");
            System.out.println("No Radio Bluetooth found... ");
            shutDownDeviceDiscovery();

        } else if ( list.size() > 0 ) {

            mylist.clear();
            for (int i=0; i < list.size(); i++) {
                mylist.add(list.get(i));

            }

            System.out.println("Selection list proceed...");

            //************************
            shutDownDeviceDiscovery();
            //************************

        }


    }

    // list problem: passer une list, afficher une liste
    public  void getCurrentReading(List<String> plist) {

        plist.clear();
        for (int i=0; i<mylist.size(); i++) {
            plist.add((String) mylist.get(i));

        }


    }

    public static void shutDownDeviceDiscovery() {
        stopWasRequested = true;
        serviceRadio.shutdown();

    }

}