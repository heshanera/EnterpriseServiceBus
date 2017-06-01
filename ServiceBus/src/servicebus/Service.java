package servicebus;

import java.util.ArrayList;

/**
 *
 * @author heshan
 */
public class Service {
    
    private String serviceName;
    private String serviceType;
    private String deadLetterDestination;
    private String authConstraint;
    private String retryPolicy;
    private int serviceId;
    private ArrayList<String> arguments;
    private String info; 

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDeadLetterDestination() {
        return deadLetterDestination;
    }

    public void setDeadLetterDestination(String deadLetterDestination) {
        this.deadLetterDestination = deadLetterDestination;
    }

    public String getAuthConstraint() {
        return authConstraint;
    }

    public void setAuthConstraint(String authConstraint) {
        this.authConstraint = authConstraint;
    }

    public String getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(String retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }
    
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
