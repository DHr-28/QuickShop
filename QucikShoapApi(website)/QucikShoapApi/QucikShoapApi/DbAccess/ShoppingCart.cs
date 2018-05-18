using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.Common;
using Microsoft.Practices.EnterpriseLibrary.Data;

namespace QucikShoapApi.DbAccess
{
    public class ShoppingCart
    {
        public static DataSet GetShopCartDetails(int ProductId, int storeUSerid, int numberofquantity)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();

                dbCommand = db.GetStoredProcCommand("GetShopCartDetails");
                db.AddInParameter(dbCommand, "@ProductId", DbType.Int32, ProductId);
                db.AddInParameter(dbCommand, "@storeUSerid", DbType.Int32, storeUSerid);
                db.AddInParameter(dbCommand, "@numberofquantity", DbType.Int32, numberofquantity);
                return db.ExecuteDataSet(dbCommand);
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (dbCommand != null)
                {
                    dbCommand.Dispose();
                    dbCommand = null;
                }
                if (db != null)
                    db = null;
            }
        }
    }
}