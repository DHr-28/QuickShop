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
    public class ProductCategory
    {

        public static DataSet ProductCategorySelectAll()
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("ProductCategorySelectAll");
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

        public static int ProductCategoryInsert(string CategoryName, string CategoryImagePath)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("ProductCategoryInsert");
                db.AddInParameter(dbCommand, "@CategoryName", DbType.String, CategoryName);
                db.AddInParameter(dbCommand, "@CategoryImagePath", DbType.String, CategoryImagePath);
                // Execute the query and return the new identity value
                int returnValue = Convert.ToInt32(db.ExecuteScalar(dbCommand));
                return returnValue;
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

        public static int ProductCategoryUpdate(string CategoryName, string CategoryImagePath, int ProductCategoryId)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("ProductCategoryUpdate");
                db.AddInParameter(dbCommand, "@CategoryName", DbType.String, CategoryName);
                db.AddInParameter(dbCommand, "@CategoryImagePath", DbType.String, CategoryImagePath);
                db.AddInParameter(dbCommand, "@ProductCategoryId", DbType.Int32, ProductCategoryId);
                // Execute the query and return the new identity value
                int returnValue = Convert.ToInt32(db.ExecuteScalar(dbCommand));
                return returnValue;
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

        public static int ProductCategoryDelete(int ProductCategoryId)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("ProductCategoryDelete");
                db.AddInParameter(dbCommand, "@ProductCategoryId", DbType.Int32, ProductCategoryId);
                // Execute the query and return the new identity value
                int returnValue = Convert.ToInt32(db.ExecuteScalar(dbCommand));
                return returnValue;
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

        public static DataSet ProductCategorySelectById(int ProductCategoryId)
        {
            Database db = null;
            DbCommand dbCommand = null;
            try
            {
                db = DatabaseFactory.CreateDatabase();
                dbCommand = db.GetStoredProcCommand("ProductCategorySelectById");
                db.AddInParameter(dbCommand, "@ProductCategoryId", DbType.Int32, ProductCategoryId);
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
    }}