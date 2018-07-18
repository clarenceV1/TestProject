package test.cai.com.testpro;

public class EosConfig {
    private String httpEndpoint;
    private String chainId;
    private boolean verbose;

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    public void setHttpEndpoint(String httpEndpoint) {
        this.httpEndpoint = httpEndpoint;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
