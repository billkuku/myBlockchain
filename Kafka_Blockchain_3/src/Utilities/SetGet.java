package Utilities;

/**
 * data will be temporary  saved in cache, related methods and vars will be set here.
 * @author beier
 *
 */

public class SetGet {
    private String preHash = "I'm first hash11111111111111111111111111111111111111111111111111";
    private String block;
    private String blockToVerify;

    public String getPreHash() {
        return preHash;
    }
    public void setPreHash(String preHash) {
        this.preHash = preHash;
    }
    
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }
    
    public String getBlockToVerify() {
        return blockToVerify;
    }
    public void setBlockToVerify(String blockToVerify) {
        this.blockToVerify = blockToVerify;
    }
}
