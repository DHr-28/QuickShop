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
    public class User
    {
        public static DataSet UserLoginCheck(String Email, String Password)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("UserLoginCheck");
                db.AddInParameter(dbCommand, "@email", DbType.String, Email);
                db.AddInParameter(dbCommand, "@pwd", DbType.String, Password);
                return db.ExecuteDataSet(dbCommand);
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


        //
        public static int UserSignUp(String Firstname, String Lastname, String Email, String Password, String Residency, String city)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("UsersInsert");
                db.AddInParameter(dbCommand, "@Firstname", DbType.String, Firstname);
                db.AddInParameter(dbCommand, "@Lastname", DbType.String, Lastname);
                db.AddInParameter(dbCommand, "@Email", DbType.String, Email);
                db.AddInParameter(dbCommand, "@Password", DbType.String, Password);
                db.AddInParameter(dbCommand, "@Residency", DbType.String, Residency);
                db.AddInParameter(dbCommand, "@city", DbType.String, city);
                db.AddInParameter(dbCommand, "@UserType", DbType.Int32, 3);
                return  Convert.ToInt32(db.ExecuteScalar(dbCommand));
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