import io.soulsdk.model.general.SoulResponse
import io.soulsdk.model.requests.PhoneRequestREQ
import io.soulsdk.model.requests.PhoneVerifyLoginREQ
import io.soulsdk.network.managers.Credentials
import io.soulsdk.sdk.SoulSDK

/**
 * @author kitttn
 */

val _apiKey = "alksjeh2429uhda9dufoadfjo892"
var _sessionToken = ""
var _login = ""
var _pass = ""
var _uid = ""

class SdkImpl {
    val soul = SoulSDK()

    init {
        soul.initialize("https://api-1.soulplatform.com", _apiKey, "fm9f91G7Y1pGD91JvMlY4wMq", AuthCredentials())
    }

    fun authorize() {
        soul.auth
                .loginWithPass("X", "X")
                .subscribe { print(it.response) }
    }

    fun turnSearchOn() {
        soul.currentUser
                .turnSearchOn()
                .flatMap { soul.users.getNextSearchResult("pageToken", "session") }
                .subscribe { print(it.response.users) }
    }

    fun turnSearchOff() {
        soul.currentUser.turnSearchOff()
                .subscribe()
    }

    fun likeUser(id: String) {
        soul.reactions.likeUser(id)
                .subscribe { println(it.response) }
    }
}

class AuthCredentials : Credentials {
    override fun getPhoneRequestREQ(phoneNumber: String?, method: String?): SoulResponse<PhoneRequestREQ> {
        return SoulResponse(PhoneRequestREQ().apply {
            apiKey = _apiKey
            setPhoneNumber(phoneNumber)
            setMethod(method)
        })
    }

    override fun getPhoneVerifyREQ(verificationCode: String?, verificationMethod: String?): SoulResponse<PhoneVerifyLoginREQ> {
        return SoulResponse(PhoneVerifyLoginREQ())
    }

    override fun getPhoneVerifyREQ(): SoulResponse<PhoneVerifyLoginREQ> {
        return SoulResponse(PhoneVerifyLoginREQ())
    }

    override fun saveSessionToken(sessionToken: String?) {
        _sessionToken = sessionToken!!
    }

    override fun saveTempLoginPass(login: String?, pass: String?) {
        _login = login!!
        _pass = pass!!
    }

    override fun clearCredentials() {
    }

    override fun getUserId(): String {
        return _uid
    }

    override fun getSessionToken(): String {
        return _sessionToken
    }

    override fun saveUserId(userId: String?) {
        _uid = userId!!
    }

    override fun getLastSuccessfulLoginSource(): String {
        return "pass"
    }

    override fun saveLastSuccessfulLoginSource(authSource: String?) {
    }

    override fun getTempLogin(): String {
        return "X"
    }

    override fun getTempPass(): String {
        return "X"
    }
}