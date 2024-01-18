# weave-server

## git convention

### branch strategy

- main : 개발 브렌치
- release : 배포 브렌치
- feature/{ticket id} : 기능 개발 브렌치

### pr naming convention 
[{지라 티켓 id}] {jira ticket name}
ex) [WEAV-15] github 설정 수정

### commit message convention(optional)

[{지라 티켓 id}] {gitmoji} {commit message}
ex) [WEAV-15] github 설정 수정

**githook 설정 방법**
```bash
sh ./githooks/install.sh
```
