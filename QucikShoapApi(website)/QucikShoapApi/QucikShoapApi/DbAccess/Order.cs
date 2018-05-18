using Microsoft.Practices.EnterpriseLibrary.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QucikShoapApi.DbAccess
{
    public class Order
    {
        public static int OrderInsert(int RetailerUserId,string OrderAddress1,string OrderAddress2,
            string OrderPincode,string OrderContactNumber)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("OrderInsert");
                db.AddInParameter(dbCommand, "@RetailerUserId", DbType.Int32, RetailerUserId);
                db.AddInParameter(dbCommand, "@OrderAddress1", DbType.String, OrderAddress1);
                db.AddInParameter(dbCommand, "@OrderAddres2", DbType.String, OrderAddress2);
                db.AddInParameter(dbCommand, "@OrderPincode", DbType.String, OrderPincode);
                db.AddInParameter(dbCommand, "@OrderContactNumber", DbType.String, OrderContactNumber);
                return Convert.ToInt32(db.ExecuteScalar(dbCommand));
            }
            catch (Exception)
            {
                throw;
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



        public static int CartInsert(int ProductId, int storeUSerid, int numberofquantity, int RetailerUserId, int OrderId)
            {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("CartInsert");
                db.AddInParameter(dbCommand, "@ProductId", DbType.Int32, ProductId);
                db.AddInParameter(dbCommand, "@storeUSerid", DbType.Int32, storeUSerid);
                db.AddInParameter(dbCommand, "@numberofquantity", DbType.Int32, numberofquantity);
                db.AddInParameter(dbCommand, "@RetailerUserId", DbType.Int32, RetailerUserId);
                db.AddInParameter(dbCommand, "@OrderId", DbType.Int32, OrderId);
                return Convert.ToInt32(db.ExecuteScalar(dbCommand));
            }
            catch (Exception)
            {
                throw;
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


        public static DataSet GetOrderDetails(int retailuserId)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();

                dbCommand = db.GetStoredProcCommand("GetOrderDetails");
                db.AddInParameter(dbCommand, "@retailuserId", DbType.Int32, retailuserId);
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