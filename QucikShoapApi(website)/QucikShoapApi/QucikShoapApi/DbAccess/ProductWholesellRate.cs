using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.Common;
using Microsoft.Practices.EnterpriseLibrary.Data;

namespace QucikShoapApi.DbAccess
{
    public class ProductWholesellRate
    {
        public static DataSet ProductDetailsByProductIdStoreId(int productid,int storeuserid)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();

                dbCommand = db.GetStoredProcCommand("ProductDetailsByProductIdStoreId");
                db.AddInParameter(dbCommand, "@ProductId", DbType.Int32, productid);
                db.AddInParameter(dbCommand, "@storeUSerid", DbType.Int32, storeuserid);
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