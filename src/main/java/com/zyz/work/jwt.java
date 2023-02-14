package com.zyz.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;


/**
 * Created by wanghh on 2021-04-26
 */
public class jwt {

//    static String kid = "a9e1257f-c24b-4e30-8f62-6c881c29ad1e";
    static String kid = "44560bf1-b908-4067-9b08-e6c6ae6d458f";

    public void jwtUtilMain() {

//        String bjtvJwkJson = "{\n" +
//                "    \"clientId\": \"9443a672-5db5-4d39-814d-3b4de56313bb\",\n" +
//                "    \"privateKey\": {\n" +
//                "        \"p\": \"1Je43QVhkSaQYeIRhbfjTl2LBI_KEyQDkUkvpdJLymaqvEEH3-PMDAn1b26NfWBJG5QqfwtZAXrMybphcgjRIHMXcsC8-h2YrChp6QNMmzj0e7i9kfCN2VuNWmJhBuUrFG1BKXYubI-z2Dl40bSPxMX0tS28iBHWgEzjrXS-MI0\",\n" +
//                "        \"kty\": \"RSA\",\n" +
//                "        \"q\": \"s8yGvfIFoVTU3qyP62G3SHysQwMRcJEyBbHKv8Pk061pYnemopOcqhuIdJbCot1zb4jwYxQhJxeyWuzhB-s63pnIdbEZ52g-2RRSBWXdLnENkDs9YOexxP7xWAymvbUGjwXj9UU-tckOr8ct79Vg8RPYtg1qsOMHLvzmV6KX2Zk\",\n" +
//                "        \"d\": \"ES0P0Ac7DlacpebHhnsRNRtZuwex7EVHj2TmYYpvhW5WUGH2-mnff0MMs7hUvlEfx_cQHU2g09Roh02e1Oi-iy4h7CxBnOzCMoLdlkPto5fKhs1VqRgqvN0-yigKPOOFzJl1iAU4fKvzeNGqrWlT18_nMqdRVwLBbRfz9ipjZbs4lSmTtRLwiQibtVQvkESMNM6jsPNLbgEGCH5v3bGUaowxyBjN8JjLRcK4VwBEeT7JnC5o6yLkG_HnsooavI_pWPcwB3Jn7JUTxK6XHERiS6IXmd6kTcj3I043cim1g-1Udye_Vqf313Pr_TbQJ3Xe-8Fc8X1tnKXxpkAXr0O7YQ\",\n" +
//                "        \"e\": \"AQAB\",\n" +
//                "        \"use\": \"sig\",\n" +
//                "        \"kid\": \"a9e1257f-c24b-4e30-8f62-6c881c29ad1e\",\n" +
//                "        \"qi\": \"kv3VVqzSlMxp3JLSOo5nW4bq1f7j_I6c0wTpgnWzGeSbfebNZtDXid97Tbf1OYhGmpEk4hKcE3Eh9xuIApkqjiWbKrZIW5QfoHJwVF9iY_iKI1iO7VyTYm3PRjdyoUbUGbNebeDxVKHrbf48OM2Gi1Kf8ubChA7Z43MqpuedYFQ\",\n" +
//                "        \"dp\": \"pt4EY6_iml0rvTkGSfBBwfQYt1bp5UZCapqIhN7hUJhvcV6WAL28HsL-XB3pfzzBhZ0yEhBlJ40tCzZ5YFndVFwiTpdMfgIXISKDpztIuRnmhOpIGjI4FSEQfZ50zEW7H8h1-kZiQCI3pwPNesUAiBJddozUy_U8ae5wU2ksE80\",\n" +
//                "        \"dq\": \"CzAY2kzrCMTskgaO1gi4jOwlE-5m8Wg3gp8zjIc1_-30dsoXNlqVDwizjv2qktPQ0QnmW2PvRC13Ta0bu5Bpk1xiWuBwSjP0ZTGcgPV7s94ZB_aHtTX1m9p5Tg9LeNaA1_l329U5N7qRDWMJerj-VUH05C8limg2FDhsZNubM1E\",\n" +
//                "        \"n\": \"lU_vDwKmx-0V62cbciYWwca1nRkxvtgG8zbDveFFb-H5O6vmv7-u0GCSYTglcT3FSrMXNHRXHVi9vZXsdXnaK2txUvZiChBWXHdlMhHbpmcYxk2BSbbZ-DB6fuP_woe27eA4jeWZT6S6pwm6fkBbUIC6_BzLjEdS84RMIpRxsPnEcK6bBcE8vG12AMF1mQNuDGYWw40NpnzMYMMyAsPKMU1ZK79AZnSgOq427wYmeP_6A_GYYy40nw-mp6kRsdpqllDZFXUzMDyn1LyadwAbouPmqs_Lo5_q1VQbVspc_OakLeoWBNnqIO1MPka0cNjalE4AvNjAewOXL39y2f2JRQ\"\n" +
//                "    }\n" +
//                "}";
        String bjtvJwkJson = "{\n" +
                "    \"clientId\": \"1f8662de-18f7-4f0b-a9a6-526921360131\",\n" +
                "    \"privateKey\": {\n" +
                "        \"p\": \"yd0-77CHok3wCDra7ZoK-fPLfWXm8FVrlVoK2WFepJRyq7ocQ5PcvlEinGDeGuzSDgx6zt3fy5z1eL4jdhI3OHefZIxlLL8UxEa2rxtWrN06GQgNZoGjExQ_ev6UEmtzeVpbQ1Mx97nQhs-Iv3MyRDS2UNFPHhN2BGxKW11D7FM\",\n" +
                "        \"kty\": \"RSA\",\n" +
                "        \"q\": \"umypmpYrFjFaw7rNFBIoKJQPt0rVVllzidfkuaZWlo5vWSifVV6HHd3Fez6MSIHbA7wXt5TuExAbUyGEcPCmDWRBIl3st01Q-PbLPg9wydssD8jH_YPF2lh2MGfLL9MtTMnaJmCGxWD1oFKHpRQvxy5y6R3JmEQLBJcdHvLQSps\",\n" +
                "        \"d\": \"cYI7DOfFcrt6PeUc4V--kdUN55QDg_l2Q4AnWWJRSZAM_Cp_-Q374zyP1MYWBADHr03qNIwhEFLqPDbEw13OfYiEsmfic1V8Rv9mfP8l2M4stoEfxObji_32LIlm4jOA6GDqWvxew86ZA4Fg6O0JCb5DgSqSz5iQmhpBOKp9RR43O06bk-fQBcMqafoIIXAb4bvu_6D4oT7VL92yzwWTRAntNPCKYjFeC3CaePTJ7M4hX8a7DmcETPEamngOfQjZw_ZjVPiRGIzMmhdFye9U5wlNvs8ONNgcm8uibR88nzwLD3dxaREshRxIv29hgMyz_XV36PYyXhy9DgihfFE1xQ\",\n" +
                "        \"e\": \"AQAB\",\n" +
                "        \"use\": \"sig\",\n" +
                "        \"kid\": \"a9e1257f-c24b-4e30-8f62-6c881c29ad1e\",\n" +
                "        \"qi\": \"ShX2-jEMP11JWqFIZ5IF91gd-AOHP1OR2q--WRvWYDXQ9ZvSZks2v9ZH_F3Zwcw83UE7oQnQV873Z1FeNrjJuO3_PM3UZsWNnl6q3Vv1IrFdTwh8u5QL0xgsNNJqfWhUuEKRPa5B5M1vQHp1LIFWJGq7B2XU7p-RT9LAjgoAqJs\",\n" +
                "        \"dp\": \"cbwbYrcGpJbe__QRN8AMISqQY3XPuWeXdw3FgRcQWRBez4zLsfOUcxKJuqwFWAxIiavUYcu29STZVY9jsXmv7TvQ7bkApEaA79mbwmtX2YvH2SSlUDUIaY6F-cmIKhyqchxLwVIVnCrwZ5R0ytGf57f_ZOeZeSuj2fgdqP6Sb4s\",\n" +
                "        \"dq\": \"HNmADbo7hL2yjCO2ZOrCaDUxkW31Qd2p8aUFcm15bOcx-Bw_PBja1BZj30S4tOfTmupayUA076JIeD7xngC_0ca4Bbd_UIaYh065-Squi65tTSwHNwZTcgzKjYcifI4zrnR2XAYIFTeCC4N6FmPfWPWpNsYzUmsFQXqZORbegQ0\",\n" +
                "        \"n\": \"kwBuzaduuWjHCjh1ws1LpFU1esqj718kT9HWeaG_f_VuDhdXikLz6R3GwFretWRW6REFNTR5DIpmsvmTQchFi323lUMzQbqAGeEOzyANO8o4_ulaanXh1XaVesebU_42_QoqtK7xF7ep2W8MLZRUHIH_N9L6BsP35jHD00diXb4gFjyxSDKPnCYBSkG0BH8--GxqwF3w7Xdfk6VQkkZwnvISZaNGw_WR0CDgcbxtGkkea3xyokQfYLM_ONSaBFKvtUJQnGyU0XooQiVTH3vF-SCXAcGPcfz-04-kzARCjjyKZ5_mV9JS7qKWv65tzrz9haOVyJRXMpsUJYOXk-AUQQ\"\n" +
                "    }\n" +
                "}";
        String ss = generateJWT(bjtvJwkJson);
        System.out.println(ss);
        String sss = "";
    }

    public static String generateJWT(String jwkJson) {
        try {
            long now = System.currentTimeMillis();
            JSONObject jwkJsonObj = JSON.parseObject(jwkJson);
            String clientId = jwkJsonObj.getString("clientId");
            RSAPrivateKey privateKey = getPrivateKey(jwkJsonObj.getString("privateKey"));
            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                    .audience("admin")
                    .expirationTime(new Date(now + 6000000))
                    .issueTime(new Date(now))
                    .issuer(clientId)
                    //添加任意数据。
                    .claim("account_type", "serviceAccount");
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.parse("RS256"))
                    .keyID(kid)
                    .type(JOSEObjectType.JWT)
                    .build();
            SignedJWT signedJWT = new SignedJWT(header, claimsBuilder.build());
            signedJWT.sign(new RSASSASigner(privateKey));
            System.err.println(signedJWT.serialize());
            return signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将秘钥字符串转换成秘钥对象。
     *
     * @param privateKey 秘钥字符串
     * @return 秘钥对象
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        Object privateKeyObj = new JSONParser(JSONParser.USE_HI_PRECISION_FLOAT |
                JSONParser.ACCEPT_TAILLING_SPACE).parse(privateKey);
        if (privateKeyObj instanceof com.nimbusds.jose.shaded.json.JSONObject) {
            com.nimbusds.jose.shaded.json.JSONObject
                    jwtObject =
                    (com.nimbusds.jose.shaded.json.JSONObject) privateKeyObj;
            // Find the RSA signing key
            if (jwtObject.get("use").equals("sig") && jwtObject.get("kty").equals("RSA")) {
                return RSAKey.parse(jwtObject).toRSAPrivateKey();
            }
        }
        return null;
    }
}
