# WEAVE <img src="https://avatars.githubusercontent.com/u/155827707?s=400&u=98d46e358a24b2d4443bdaa595d1d33541cafefb&v=4" align=left width=100>

> 안전한 대학생 미팅 플랫폼, WEAVE &nbsp;&nbsp; • <b>백엔드</b> 레포지토리

<br>

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Student-Center_weave-server&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Student-Center_weave-server)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Student-Center_weave-server&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Student-Center_weave-server)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Student-Center_weave-server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Student-Center_weave-server)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Student-Center_weave-server&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Student-Center_weave-server)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Student-Center_weave-server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Student-Center_weave-server)

---

## 📌 WEAVE?

WEAVE는 대학생들이 새로운 인연을 만들 수 있도록 돕는 미팅 플랫폼입니다. 대학교 인증을 통해 신뢰할 수 있는 대학생들과 연결되며, 익명성을 바탕으로 안전하고 편안한 소통이 가능합니다. 또한, 사용자가 작성한 프로필을 기반으로 관심사가 비슷한 상대방을 추천받을 수 있어, 보다 의미 있는 만남을 기대할 수 있습니다. WEAVE와 함께 캠퍼스 라이프를 더욱 풍성하게 만들어보세요!

<p float="left"> <img src="https://github.com/Student-Center/weave-server/assets/28651727/66f03093-5b81-4e0d-bc9f-2540d413bfe0" width="300" /> <img src="https://github.com/Student-Center/weave-server/assets/28651727/9ef05940-25e2-4027-bb0f-e82cb1549a31" width="300" /> </p> <p float="left"> <img src="https://github.com/Student-Center/weave-server/assets/28651727/1312eed8-b41a-43fa-9d1c-7ead32831f94" width="300" /> <img src="https://github.com/Student-Center/weave-server/assets/28651727/212deb78-ed2e-4b12-9ce8-0d3662b27eec" width="300" /> </p>

---

## 🏗 Git Convention

### 🎋 Branch Strategy

- main : 개발 브렌치
- release : 배포 브렌치
- feature/{ticket id} : 기능 개발 브렌치

### PR Naming Convention

[{지라 티켓 id}] {jira ticket name}
ex) [WEAV-15] github 설정 수정

### Commit Message convention(optional)

[{지라 티켓 id}] {gitmoji} {commit message}
ex) [WEAV-15] github 설정 수정

**githook 설정 방법**

```bash
sh ./.githooks/install.sh
```

## 🛰️ Architecture

### Infrastructure - AWS

![weave-infra.jpg](./docs/weave-infra.jpg)
