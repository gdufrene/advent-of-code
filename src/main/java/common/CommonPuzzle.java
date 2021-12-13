package common;

public abstract class CommonPuzzle {
    
    protected DataIterator it;
    
    public CommonPuzzle() {
        
    }
    
    public CommonPuzzle load(String filePath) {
        it = new DataIterator(filePath);
        return this;
    }
    
    public abstract void solve();

}
