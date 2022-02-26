fileName = 'cv1.mp4';
obj = VideoReader(fileName);   % 读入原视频
numFrames = obj.NumberOfFrames;% 帧的总数
frame1 = read(obj,1);
frame1 = imresize(frame1,[609,360]);
for k = 1 : numFrames          % 读取数据
frame = read(obj,k);
frame = imresize(frame,[640,360]);
frame1(3*k-2:3*k,:) = frame(3*k-2:3*k,:);
%imshow(frame1);%显示帧
%imshow(frame);
%imwrite(frame,strcat(num2str(k),'.jpg'),'jpg');% 保存帧
end

%生成新的视频文件
v = VideoWriter('newfile.avi','Uncompressed AVI');
open(v)
for k = 1 : numFrames
    frame_out = read(obj,k);
    frame_out = imresize(frame_out,[640,360]);
    frame_out(1:3*k,:) = frame1(1:3*k,:);
    frame_out(3*k,:) = 255;  %白线
    %imshow(frame_out)
    writeVideo(v,frame_out)
end
close(v)




