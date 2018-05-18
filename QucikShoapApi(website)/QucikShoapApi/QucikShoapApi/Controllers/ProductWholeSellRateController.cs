using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data;
using QucikShoapApi.Models;

namespace QucikShoapApi.Controllers
{
    public class ProductWholeSellRateController : ApiController
    {

        //1.100
        //http://localhost:54433/QShoap/ProductDetailsByProductIdStoreId
        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/ProductDetailsByProductIdStoreId")]
        public IHttpActionResult ProductDetailsByProductIdStoreId(int productid,int storeuserid)
        {
            try
            {

                System.Data.DataSet ds = DbAccess.ProductWholesellRate.ProductDetailsByProductIdStoreId(productid, storeuserid);
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new
                {

                    Rate = p.Field<decimal>("Rate"),
                    Firstname = p.Field<string>("Firstname"),
                    Lastname = p.Field<string>("Lastname"),
                    Email = p.Field<string>("Email"),
                    Residency = p.Field<string>("Residency"),
                    city = p.Field<string>("city"),
                    ProductId = p.Field<int>("ProductId"),
                    Userid= p.Field<int>("Userid"),

                }).ToList();


                var lst2 = ds.Tables[1].AsEnumerable().Select(p => new
                {
                    QuantityRange1 = p.Field<int>("QuantityRange1"),
                    QuantityRange2 = p.Field<int>("QuantityRange2"),
                    DiscountPercentage = p.Field<decimal>("DiscountPercentage")

                }).ToList();
                return Ok(new
                {
                    productratedetails = lst,
                    productwholesellrate=lst2
                });
            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }



    }
}