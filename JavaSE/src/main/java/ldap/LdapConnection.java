package ldap;

import java.util.ArrayList;
import java.util.List;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.controls.SubentriesRequestControl;

import pojo.UserAccountInfo;

public class LdapConnection {

    private static String host = "192.168.0.51";
    private static int port = 389;
    private static String bindDN = "cn=root";     //用来认证的DN
    private static String bindPassword = "Sioc123!";  //用来认证的密码
    private static LDAPConnection connection = null;
    
    public static void openConnection() {
        if (connection == null) {
            try {
                connection = new LDAPConnection(host, port, bindDN, bindPassword);
            } catch (Exception e) {
                System.out.println("连接LDAP出现错误：\n" + e.getMessage());
            }
        }else if(connection.isConnected()){
            System.out.println("**********LDAP已连接***********");
        }else{
            System.out.println("**********LDAP连接中断，进行重迄1�7***********");
            try{
                connection.reconnect();
            } catch (Exception e) {
                System.out.println("重连LDAP出现错误：\n" + e.getMessage());
            }
        }
    }
    
    /** 查询 
     * @param baseDN  查询的起始点
     * @param filter    匹配条件
     * @return
     */
    public static SearchResult queryLdap(String baseDN, String filter) {
        SearchResult searchResult = null;
        try {
            // 连接LDAP
            openConnection();
            
            // 执行查询    searchDN为查询的起始点    范围SUB把基准DN及其以下的整棵子树都作为搜索对象    filter是匹配条件如"unit=1900"
            SearchRequest searchRequest = new SearchRequest(baseDN, SearchScope.SUB, "(" + filter + ")");
            searchRequest.addControl(new SubentriesRequestControl());
            searchRequest.setAttributes("*","+");
            searchResult = connection.search(searchRequest);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查询错误，错误信息如下：\n" + e.getMessage()+"\n SearchDN:"+baseDN);
        } finally {
            if (connection != null)
                connection.close();
        }
        return searchResult;
    }
    
     
    public static List<UserAccountInfo> getUsers() {
        List<UserAccountInfo> userList = new ArrayList<UserAccountInfo>();
        try {
            SearchResult orgResult = queryLdap("cn=users,dc=sioc,dc=ac,dc=cn", "objectclass=person");
            for (int i = 0; i < orgResult.getSearchEntries().size(); i++) {
                SearchResultEntry entry = orgResult.getSearchEntries().get(i);
                UserAccountInfo user = new UserAccountInfo();
                user.setAccount_name(entry.getAttributeValue("uid"));
                user.setUser_age(Integer.parseInt(entry.getAttributeValue("age")==null || entry.getAttributeValue("age")==""?"0":entry.getAttributeValue("age")));
                user.setProfessional_grade(entry.getAttributeValue("band"));
                user.setCard_no(entry.getAttributeValue("scardsnr"));
                user.setCard_status(entry.getAttributeValue("cardstatus"));
                user.setUser_name(entry.getAttributeValue("cn"));
                user.setDepart_code(entry.getAttributeValue("departcode"));
                user.setDepart_sn(entry.getAttributeValue("DepartSN"));
                user.setEmployee_type(entry.getAttributeValue("employeeType"));
                user.setIdentity_id(entry.getAttributeValue("IDInfo"));
                user.setOrg_name(entry.getAttributeValue("ou"));
                user.setUser_account_id(entry.getAttributeValue("outid"));
                user.setUser_sex(entry.getAttributeValue("sex"));
                user.setCivil_status(entry.getAttributeValue("civilState"));
                //读取电话数据
                String phone = entry.getAttributeValue("mobile");
                if(phone==null){
                    phone = entry.getAttributeValue("telephoneNumber");
                }
                user.setTelephone_number(phone);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static void main(String[] args) {
        List<UserAccountInfo> list = getUsers();
        
        for (UserAccountInfo userInfo : list) {
            
            System.out.println(userInfo);
        }
    }
}

