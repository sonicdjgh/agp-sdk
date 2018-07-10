## EGLS Game SDK 서버 연동 문서 V1.0.1

### 1. 설명
이문서는 두가지 서버 연동 설명이 포함 되여 있습니다.

#### 1. Token검증
클라이언트 에서 SDK로 로그인시,서버에서token검증을 추가해야 합니다 매번 로그인시,서버는 반드시 서버token을 검증해야 하며,개발사에서 EGLS SDK 서버로 요청을 합니다.

#### 2. 결제 정보 Return
클라이언트 결제 완성 후,개발사에서는 EGLS SDK 서버에서 보내는 호출을 받을 수 있어야 하며,서로 결제 정보를 동기화 합니다.
<br /><br />

### 2. Server interaction

#### 1. 코드격식
UTF-8격식 코드사용.

#### 2. 연동설명

##### 2.1 token검증（GET）

##### 2.1.1 연동주소
地区 | 地址
---|---
중국 | http://cnpassport.eglsgame.com/passport/egls/tokenVerify
대만 | http://twpassport.eglsgame.com/passport/egls/tokenVerify
한국 | http://krpassport.eglsgame.com/passport/egls/tokenVerify

##### 2.1.2 호출설명
이름 | 설명 | 필요성
---|---|---
uid | EGLS유저Id | 是
ticket | 유저 검증표 | 是
appId | App ID(EGLS에세 세팅합니다) | 	是
sign | 대문자 sign코드,MD5(appId+uid+ticket+secret) | 是

**예시**：http://twpassport.eglsgame.com/passport/egls/tokenVerify?uid=123456&ticket=abcdefggg&appId=1&sign=E866C08C984405C3DBD39ECAE1ED5224

##### 2.1.3 피드백
{“code”:0,“message”:”피드백 유효”}

##### 2.2 결제 정보 피드백 연동(POST，개발사에서 제공)

##### 2.2.1 연동 주소
개발사에서 제공.

##### 2.2.2 코드 설명
이름 | 설명 | 필요성
---|---|---
appId | 상품 앱아이디 | Yes
cpOrder | 개발사 자체 데이터(개발사 주문번호로 권장합니다),SDK를 호출하여 결제 연결시 개발사에서 전달해 줍니다 | Yes
money | 충전금액 | Yes
payTime | 결제시간 | Yes
order | EGLS 주문번호 | Yes
currency | 화페단위 | Yes
sandbox | 테스트 환경, true or false | Yes
sign | 대문자signdata비여 있지 않은 코드 관련하여 높은 순위로 정렬합니다(sign제외),”&”로 각 코드를 연결해주며,sign번호를 만듭니다,sign끝에는 appsecret를 추가합니다(EGLS 제공) | Yes

예시：http://xxx.xx.xx/notify?appId=000&cpOrder=xxxxxxxxxxxxx&money=1.0&payTime=1486530505000&order=5E3DC6F52063A2DD51057B870206E6&currency=RMB&sandbox=false&sign=E866C08C984405C3DBD39ECAE1ED5224

签名源串示例（假设密钥为AAAAAA）：
000&xxxxxxxxxxxxx&RMB&1.0&5E3DC6F52063A2DD51057B870206E6&1486530505000&falseAAAAAA

##### 2.2.3 피드백（개발사에서 Return해야할 데이터）
결제성공시success로 피드백을 줘야하며,기타는 실패로 인정함.

**注意**：如收到通知不返回success, SDK平台会重复发送订单。
<br /><br />

### 첨부

token검증 시 오류코드.

code | message
---|---
1 | 검증실패
2 | Session기간초과
3 | Session무효
99 | 异常，请检查appId是否为EGLS提供的商户ID
