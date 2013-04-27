using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Runtime.Serialization;


namespace Ctf
{
    // reference : http://mobile.dzone.com/news/windows-phone-7-serialization-0
    [DataContractAttribute]
    public class User
    {
        [DataMember]
        public string username { get; set; }
        [DataMember]
        public string access_token { get; set; }
        [DataMember]
        public string token_type { get; set; }
        [DataMember]
        public string scope { get; set; }

        public User()
        {
            username = String.Empty;
            access_token = String.Empty;
            token_type = String.Empty;
            scope = String.Empty;
        }

        public User(string username, string access_token, string token_type, string scope)
        {
            if (username != null)
                this.username = username;
            else
                this.username = String.Empty;

            if (access_token != null)
                this.access_token = access_token;
            else
                this.access_token = String.Empty;

            if (token_type != null)
                this.token_type = token_type;
            else
                this.token_type = String.Empty;

            if (scope != null)
                this.scope = scope;
            else
                this.scope = String.Empty;
        }

        public bool HasNullOrEmpty()
        {
            return (String.IsNullOrEmpty(username) || String.IsNullOrEmpty(access_token) || String.IsNullOrEmpty(token_type) || String.IsNullOrEmpty(scope));
        }

        public override string ToString()
        {
            string userJSON = "{ ";
            userJSON += "\"username\" : \"" + username + "\", ";
            userJSON += "\"access_token\" : \"" + access_token + "\", ";
            userJSON += "\"token_type\" : \"" + token_type + "\", ";
            userJSON += "\"scope\" : \"" + scope + "\" ";
            userJSON += " }";
            return userJSON;
        }
    }
}
