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
    public class SubProductCategory
    {
        public static DataSet SubProductCategorySelectAll(int id=0)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
               
                dbCommand = db.GetStoredProcCommand("SubProductCategorySelectAll");
                db.AddInParameter(dbCommand, "@ProductCategoryId", DbType.Int32, id);
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