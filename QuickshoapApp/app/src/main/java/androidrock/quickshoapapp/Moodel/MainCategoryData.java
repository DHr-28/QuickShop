package androidrock.quickshoapapp.Moodel;

public class MainCategoryData {


    public MainCategoryData(String CategoryName, String CategoryImagePath,Integer idpk){
        this.CategoryName = CategoryName;
        this.CategoryImagePath = CategoryImagePath;
        this.ProductCategory_ID = idpk;
    }


    public Integer ProductCategory_ID;
    public String CategoryName;
    public String CategoryImagePath;
}


