using Microsoft.Practices.EnterpriseLibrary.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DbAccess
{
    public class User
    {

        //
        public static DataSet UserLoginCheck(String username,String pwd)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("UserLoginCheck");
                db.AddInParameter(dbCommand, "@email", DbType.String, username);
                db.AddInParameter(dbCommand, "@pwd", DbType.String, pwd);
                DataSet dsCompanyInfo = (DataSet)db.ExecuteDataSet(dbCommand);
                return dsCompanyInfo;
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

    }
}
