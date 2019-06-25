YESTERDAY=`date "+%Y-%m-%d %H:%M:%S"` 
echo $YESTERDAY


X=$RANDOM
Y=29
Num=$((X % Y)) 
emoji=''
case $Num in     
	1)  emoji=':pencil2:'     ;;     
	2)  emoji=':globe_with_meridians:'     ;;     
	3)  emoji=':wrench:'     ;;     
	4)  emoji=':heavy_plug_sign:'     ;;     
	5)  emoji=':whale:'     ;;   
	6)  emoji=':heavy_minus_sign:'     ;;   
	7)  emoji=':hammer:'     ;;   
	8)  emoji=':chart_with_upwards_trend:'     ;;   
	9)  emoji=':construction_worker:'     ;;   
	10)  emoji=':arrow_up:'     ;;   
	11)  emoji=':arrow_down:'     ;;   
	12)  emoji=':green_heart:'     ;;   
	13)  emoji=':construction:'     ;;   
	14)  emoji=':rotating_light:'     ;;   
	15)  emoji=':bookmark:'     ;;   
	16)  emoji=':checked_flag:'     ;;   
	17)  emoji=':penguin:'     ;;   
	18)  emoji=':apple:'     ;;   
	19)  emoji=':lock:'     ;;   
	20)  emoji=':white_check_mark:'     ;;   
	21)  emoji=':tada:'     ;;   
	22)  emoji=':lipstick:'     ;;   
	23)  emoji=':rocket:'     ;;   
	24)  emoji=':memo:'     ;;   
	25)  emoji=':sparkles:'     ;;  
	26)  emoji=':ambulance:'     ;;  
	27)  emoji=':bug:'     ;;  
	28)  emoji=':fire:'     ;;   
	*)  emoji=':art:'     ;; 
esac

 

cd /Users/liuchen/Developer/LZHS/Development/GitHubProjrects/ModularizationDemo

echo "$emoji 脚本自动提交，提交时间$YESTERDAY"
 
git pull

git status

git add . 

git commit -m "$emoji 脚本自动提交，提交时间$YESTERDAY"

git push -u origin master 


     
