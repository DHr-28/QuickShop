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
    public class ShopCartController : ApiController
    {

        //1.100
        //http://localhost:54433/QShoap/ShopCart
        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/GetShopCartDetails")]
        public IHttpActionResult GetShopCartDetails(int ProductId, int storeUSerid, int numberofquantity)
        {
            try
            {

                int retunrnumberofquantity = numberofquantity;
                int manageProductId = ProductId;
                int managestoreUSerid = storeUSerid;
                System.Data.DataSet ds = DbAccess.ShoppingCart.GetShopCartDetails(ProductId, storeUSerid, numberofquantity);
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new
                {

                    basicrate = p.Field<decimal>("basicrate"),
                    wholeselldiscount = p.Field<decimal>("wholeselldiscount"),
                    totalValue = p.Field<decimal>("totalValue"),
                    ImagePath = p.Field<string>("ImagePath"),
                    Description = p.Field<string>("Description"),
                    ShoapMasterName = p.Field<string>("ShoapMasterName"),
                    totalNetValue = p.Field<decimal>("totalNetValue"),
                    numberofqunatity = retunrnumberofquantity,
                    ProductId = manageProductId,
                    wholesellersotreid = managestoreUSerid
                }).ToList();



                return Ok(new
                {
                    lstdata = lst,

                });
            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }



        //http://localhost:54433/QShoap/GetOrderDetails
        [System.Web.Http.HttpGet]
        [System.Web.Http.Route("~/QShoap/GetOrderDetails")]
        public IHttpActionResult GetOrderDetails(int retailuserId)
        {
            try
            {

                System.Data.DataSet ds = DbAccess.Order.GetOrderDetails(retailuserId);
                int rowcount = ds.Tables[0].Rows.Count;
                var lst = ds.Tables[0].AsEnumerable().Select(p => new
                {

                    ImagePath = p.Field<string>("ImagePath"),
                    SubCategoryName = p.Field<string>("SubCategoryName"),
                    OrderDate = p.Field<DateTime>("OrderDate"),
                    Status = p.Field<string>("Status"),
                    totalNetValue = p.Field<decimal>("totalNetValue"),
                    ShoapMasterName = p.Field<string>("ShoapMasterName")
                }).ToList();



                return Ok(new
                {
                    lstdata = lst,

                });
            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }

    }
}