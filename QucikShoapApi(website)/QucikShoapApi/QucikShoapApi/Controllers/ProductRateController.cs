using QucikShoapApi.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace QucikShoapApi.Controllers
{
    public class ProductRateController : ApiController
    {
        //1.100
        //http://localhost:54433/QShoap/ProductRateByProductId
        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/ProductRateByProductId")]
        public IHttpActionResult ProductRateByProductId(int id = 0)
        {
            try
            {

                System.Data.DataSet ds = DbAccess.ProductRate.ProductRateByProductId(id);
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new
                {
                    SubProductCategoryId = p.Field<int>("SubProductCategoryId"),
                    SubCategoryName = p.Field<string>("SubCategoryName"),
                    Description = p.Field<string>("Description"),
                    Rate = p.Field<decimal>("Rate"),
                    Email = p.Field<string>("Email"),
                    Userid = p.Field<int>("Userid"),
                    ImagePath = p.Field<string>("ImagePath")

                }).ToList();
                return Ok(new
                {
                    lstdata = lst
                });
            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }

    }
}