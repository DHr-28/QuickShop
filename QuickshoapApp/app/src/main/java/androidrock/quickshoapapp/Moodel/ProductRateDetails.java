package androidrock.quickshoapapp.Moodel;

/**
 * Created by Vigo Telecome on 22-12-2016.
 */
public class ProductRateDetails {
    public Integer SubProductCategoryId;
    public String SubCategoryName;
    public String Description;
    public double Rate;
    public String Email;
    public int Userid;
    public String imgpath;

    public ProductRateDetails(Integer subProductCategoryId, String subCategoryName, String description,
                              double rate, int userid, String email,String ImgPath) {
        SubProductCategoryId = subProductCategoryId;
        SubCategoryName = subCategoryName;
        Description = description;
        Rate = rate;
        Userid = userid;
        Email = email;
        imgpath=ImgPath;
    }
}
