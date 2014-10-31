package adapter

import scala.concurrent.Future
import scala.util.{ Try, Success, Failure }
import org.slf4j.LoggerFactory

//import com.amazonaws.auth._
//import com.amazonaws.services.s3.model._
//import com.amazonaws.services.s3.AmazonS3Client
//import com.amazonaws.ClientConfiguration
//import com.amazonaws.Protocol

trait S3ClientLike {
	def readFileAsString(path: String): Try[String]
	def readFileAsByteArray(backetUrl: String, path: String): Try[Array[Byte]]
	def readFileAsByteArray(path: String): Try[Array[Byte]]
	def deleteFile(path: String): Boolean
	def copyFileAsync(path: String, destinationBucketName: String, destinationPath: String): Future[Try[Long]]
	def getBucketNameFromUrl(url: String): String = {
		url.split("://")(1).split('.')(0)
	}
}

//class S3Client(awsKey: String, awsSecret: String, bucket: String)(implicit execctx: scala.concurrent.ExecutionContext)
//	extends S3ClientLike {
//
//	def logger = LoggerFactory.getLogger(this.getClass)
//
//	val bucketName = getBucketNameFromUrl(bucket)
//	lazy val client: AmazonS3Client = getClient
//
//	def getBucketName = {
//		bucket
//	}
//
//	def getClient = {
//		val clientConfig = new ClientConfiguration();
//		clientConfig.setProtocol(Protocol.HTTPS);
//		val cl = new AmazonS3Client(new BasicAWSCredentials(awsKey, awsSecret), clientConfig)
//		cl.setRegion(com.amazonaws.regions.Region.getRegion(com.amazonaws.regions.Regions.US_EAST_1))
//		cl.setEndpoint("s3.amazonaws.com")
//		cl
//	}
//
//	def readFileAsString(path: String): Try[String] = {
//		Try {
//			val request = new GetObjectRequest(bucketName, path.stripPrefix("/"))
//			val file = client.getObject(request)
//			Source.fromInputStream(file.getObjectContent())(io.Codec("UTF-8")).mkString
//		}
//	}
//
//	def readFileAsByteArray(path: String): Try[Array[Byte]] = {
//		readFileAsByteArray1(bucketName, path.stripPrefix("/"))
//	}
//
//	def readFileAsByteArray(backetUrl: String, path: String): Try[Array[Byte]] = {
//		readFileAsByteArray1(getBucketNameFromUrl(backetUrl), path.stripPrefix("/"))
//	}
//
//	private def readFileAsByteArray1(backetName: String, path: String): Try[Array[Byte]] = {
//		Try {
//			val request = new GetObjectRequest(bucketName, path)
//			val file = client.getObject(request)
//			val is = new BufferedInputStream(file.getObjectContent())
//			val fragmentBytes = IOUtils.toByteArray(is)
//			file.close()
//			is.close()
//			fragmentBytes
//		}
//	}
//
//	def deleteFolder(pathIn: String): Try[Boolean] = {
//
//		val path = pathIn.stripPrefix("/").stripSuffix("/")
//
//		val filesList = client.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(path)).getObjectSummaries().toList
//
//		val multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName).withQuiet(false)
//
//		val keys = filesList.map(file => new KeyVersion(file.getKey()))
//
//		multiObjectDeleteRequest.setKeys(keys)
//
//		Try(keys.length match {
//			case l if l > 0 => client.deleteObjects(multiObjectDeleteRequest)
//			case _ => Success(true)
//		}).map(x => client.deleteObject(bucketName, path)).map(x => true)
//	}
//
//	def save(path: String, out: ByteArrayInputStream): Try[String] = {
//		save1(bucketName, path, out).map(t => bucket + "/" + path.stripPrefix("/"))
//	}
//
//	def save(backetUrl: String, path: String, out: ByteArrayInputStream): Try[Boolean] = {
//		save1(getBucketNameFromUrl(backetUrl), path, out)
//	}
//
//	private def save1(backetName: String, path: String, out: ByteArrayInputStream): Try[Boolean] = {
//		Try {
//			val meta = new ObjectMetadata()
//			meta.setContentLength(out.available())
//			meta.setContentType("image/jpeg")
//			val putObjectRequest = new PutObjectRequest(bucketName, path.stripPrefix("/"), out, meta)
//			val putObjectRequestWithAcess = putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead)
//			client.putObject(putObjectRequestWithAcess)
//			true
//		}
//	}
//
//	def deleteFile(path: String): Boolean = {
//		client.deleteObject(bucketName, path.stripPrefix("/"))
//		true
//	}
//
//	def copyFileAsync(path: String, destinationBucketUrl: String, destinationPath: String): Future[Try[Long]] = {
//		Future {
//			try {
//				val orig = path.stripPrefix("/")
//				val dest = destinationPath.stripPrefix("/")
//
//				val destinationBucketName = getBucketNameFromUrl(destinationBucketUrl)
//				val copyObjRequest = new CopyObjectRequest(bucketName, orig, destinationBucketName, dest)
//				client.copyObject(copyObjRequest)
//
//				// set permission read
////				val acl = client.getObjectAcl(destinationBucketName, dest)
////				acl.grantPermission(null, Permission.Read)
////				client.setObjectAcl(destinationBucketName, dest, acl)
//				
//				val metadataRequest = new GetObjectMetadataRequest(destinationBucketName, dest)
//				val metadataResult = client.getObjectMetadata(metadataRequest)
//
//				Success(metadataResult.getContentLength())
//			} catch {
//				case e: Exception => {
//					val str = e.toString
//					Failure(e)
//				}
//			}
//		}(execctx)
//	}
//
//	def fileExists(path: String): Future[Try[Boolean]] = {
//		Future {
//			try {
//				val metadataRequest = new GetObjectMetadataRequest(bucket, path)
//				val response = client.getObjectMetadata(metadataRequest)
//				Success(true)
//			} catch {
//				case e: AmazonS3Exception if e.getStatusCode() == 404 => Success(false)
//				case e: Throwable => {
//					val str = e.toString
//					Failure(e)
//				}
//			}
//		}(execctx)
//	}
//}

class S3Adapter(awsKey: String, awsSecret: String, bucket: String)(implicit execctx: scala.concurrent.ExecutionContext)
	//extends S3Client(awsKey, awsSecret, bucket) {
{
  
}
