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
    public class ProductCategoryController : ApiController
    {
        //1.100
        //http://localhost:54433/QShoap/ProductCategorySelectAll
        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/ProductCategorySelectAll")]
        public IHttpActionResult ProductCategorySelectAll()
        {
            try
            {

                System.Data.DataSet ds = DbAccess.ProductCategory.ProductCategorySelectAll();
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new 
                {
                    ProductCategory_ID = p.Field<int>("ProductCategoryId"),
                    CategoryName = p.Field<string>("CategoryName"),
                    CategoryImagePath = p.Field<string>("CategoryImagePath")
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


        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/SubProductCategorySelectAll")]
        public IHttpActionResult SubProductCategorySelectAll(int id=0)
        {
            try
            {

                System.Data.DataSet ds = DbAccess.SubProductCategory.SubProductCategorySelectAll(id);
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new
                {
                    SubProductCategoryId = p.Field<int>("SubProductCategoryId"),
                    SubCategoryName = p.Field<string>("SubCategoryName"),
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