# 당근마켓 클론 프로젝트

## Gradle
```
dependencies {
    implementation 'com.google.firebase:firebase-database'
    implementation platform('com.google.firebase:firebase-bom:26.7.0')
    implementation 'me.relex:circleindicator:1.2.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    compile 'com.makeramen:roundedimageview:2.3.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    implementation 'info.androidhive:fontawesome:0.0.5'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    compileOnly 'org.projectlombok:lombok:1.18.16'
    annotationProcessor 'org.projectlombok:lombok:1.18.16'
    }
```

## 서버세팅
##### https://github.com/dlfl1506/daangnapp-server


## 📱 기능

### 1. GPS 로 현재위치 받아서 근처주소 뿌려주기 및 주소검색
<img src= "https://user-images.githubusercontent.com/74044212/113663882-c31bfa80-96e5-11eb-833e-9e4e49c97950.gif" width="300px" height="300px" />

##### 먼저 https://www.juso.go.kr/ 주소 홈페이지에서 좌표제공API를 디비에저장했습니다. 
##### 이후 LocationListener ,LocationManager 로 위도,경도를 받아왔습니다.
##### 하지만 좌표제공API에서 제공해주는 X,Y좌표는 UTM-K 좌표계이기 때문에 
##### 위도,경도를 받아온것을 UTM-K로 변환해주는 Proj 라이브러리를 사용하였습니다. 
<img src= "https://user-images.githubusercontent.com/74044212/113664822-69b4cb00-96e7-11eb-925b-505f69bae072.png" width="100%" height="300px" />

##### 변환해주고 UTM-K x,y좌표 범위를 +-1800 정도 근처주소들을 전부 뿌려주었습니다.
```
@Query(value = "SELECT * FROM Location WHERE entX>=?1-1800 AND entX<=?1+2000 AND entY>=?2-1800 AND entY<=?2+1800 order by entX  ASC",nativeQuery = true)
List<Location> 근처주소검색(double entX,double entY);
```

### 2. SMS 문자인증

<img src= "https://user-images.githubusercontent.com/74044212/113665071-ce702580-96e7-11eb-89ea-7d24004cbbbb.gif" width="300px" height="500px" />

##### 문자보내는건 https://console.coolsms.co.kr/ 이 사이트를 이용했습니다.
##### 문자인증받기 버튼을 누를시 문자를 보내면서 랜덤함수를 사용해 4자리의 난수 인증코드를 보내고 인증코드를 Auth 디비에 저장하였습니다. 
##### (이미 인증코드를 받았으면 SAVE 말고 UPDATE 시킴)
<img src= "https://user-images.githubusercontent.com/74044212/113665471-7dacfc80-96e8-11eb-9cf1-1f588bc099a8.png" width="200px" height="200px" />

##### 그리고 다른 스레드를 써서 40초후에 삭제하게하였고 (인증코드 만료) 
##### 디비에 있는 인증코드와 일치할시 닉네임 엑티비티로 이동하게하였습니다.
##### (이미 유저가있으면 메인엑티비티)

### 3. 게시물 구별 로 뿌려주기,다른동네선택, 게시물검색기능 

<img src= "https://user-images.githubusercontent.com/74044212/113665838-265b5c00-96e9-11eb-8e4a-d8e3cbf6b4b0.gif" width="300px" height="500px" />


### 4. 게시물쓰기


<img src= "https://user-images.githubusercontent.com/74044212/113666124-b26d8380-96e9-11eb-8988-fcd2b341af7b.gif" width="300px" height="500px" />

##### 완료버튼을 누르면 post 테이블에 post 요청을 함 과 동시에 Firebase storage에 사진을 저장했습니다.
##### 그리고 스토리지에 저장한 파일을 가져와서 uri로변환해주고 image 테이블에 post요청을 했습니다.

### 5. 게시물 상세보기 및 판매자의 다른상품 상세보기

<img src= "https://user-images.githubusercontent.com/74044212/113666461-48a1a980-96ea-11eb-88d2-790373277efd.gif" width="300px" height="500px" />

### 6. 내 게시물 수정,삭제

<img src= "https://user-images.githubusercontent.com/74044212/113669283-7ee12800-96ee-11eb-97c1-9b3c449c5998.gif" width="300px" height="500px" />

### 7. 유저 프로필 ,닉네임 변경

<img src= "https://user-images.githubusercontent.com/74044212/113667110-47bd4780-96eb-11eb-8337-0c09e36f0559.jpg" width="300px" height="500px" />

<img src= "https://user-images.githubusercontent.com/74044212/113666487-4e978a80-96ea-11eb-9ba8-cc4d6f110bcd.gif" width="300px" height="500px" />


### 8. 유저의 판매내역 - 상세보기

<img src= "https://user-images.githubusercontent.com/74044212/113667029-29574c00-96eb-11eb-9da1-e5a658f54ab7.jpg" width="300px" height="500px" />

<img src= "https://user-images.githubusercontent.com/74044212/113666673-94545300-96ea-11eb-8889-21bfe9c49ba4.jpg" width="300px" height="500px" />

### 9. 채팅

<img src= "https://user-images.githubusercontent.com/74044212/113666732-aa621380-96ea-11eb-9a2a-1a319deea92b.jpg" width="300px" height="500px" />

##### 채팅기능은 firebase 실시간데이터이스를 이용했습니다.

### 10. 앱설정(로그아웃기능)

<img src= "https://user-images.githubusercontent.com/74044212/113666820-d2ea0d80-96ea-11eb-8c62-36ab3790be1f.jpg" width="300px" height="500px" />
<img src= "https://user-images.githubusercontent.com/74044212/113666834-d8475800-96ea-11eb-8c84-968c40ebff5f.jpg" width="300px" height="500px" />


## 시연영상


<a href="https://www.youtube.com/watch?v=chVSDKkT1_w">
    <img src= "https://user-images.githubusercontent.com/74044212/113954493-e1543880-9854-11eb-86b2-d50f75b3d722.png" width="300px" height="300px" />
</a>

##### ↑↑↑↑ 사진클릭 !! 
