package pattern.builder.model;

public class TraditionalLog {
    private int productId;
    private enum productType {
        LEGACY, DEAL, PRODUCT, TOUR, TICKET
    }
    private int clickCount;
    private int salesCount;

    public TraditionalLog() {
    }

    public TraditionalLog(int productId, int clickCount, int salesCount) {
        this.productId = productId;
        this.clickCount = clickCount;
        this.salesCount = salesCount;
    }

    public int getProductId() {
        return productId;
    }

    public int getClickCount() {
        return clickCount;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    @Override
    public String toString() {
        return "TraditionalLog{" +
                "productId=" + productId +
                ", clickCount=" + clickCount +
                ", salesCount=" + salesCount +
                '}';
    }
}
