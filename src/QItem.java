class QItem{ 
    int row; 
    int col; 
    int dist; 
    public QItem(int x, int y, int w){ 
        this.row=x;
        this.col=y;
        this.dist=w;
    }
    public QItem(int x, int y){ 
        this.row=x;
        this.col=y;
    }
}
